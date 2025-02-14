package shop.sunsetsouol.registry;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import cn.hutool.json.JSONUtil;
import io.etcd.jetcd.ByteSequence;
import io.etcd.jetcd.Client;
import io.etcd.jetcd.KV;
import io.etcd.jetcd.KeyValue;
import io.etcd.jetcd.Lease;
import io.etcd.jetcd.options.GetOption;
import io.etcd.jetcd.options.PutOption;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import shop.sunsetsouol.config.RegistryConfig;
import shop.sunsetsouol.model.ServiceMetaData;

import java.nio.charset.StandardCharsets;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author YinJunBiao
 * @date 2025/2/5 22:11
 * @Description
 */
@Slf4j
public class EtcdRegistry implements Registry {

    private Client client;

    private final String root = "/rpc/service/";

    /**
     * 本地注册到注册中心的服务
     */
    private final Set<String> localRegisterNodeKeySet = new HashSet<>();
    @Override
    public void init(RegistryConfig registryConfig) {
        client = Client.builder()
                .endpoints(registryConfig.getAddress())
                .build();
        heartbeat();
    }

    @Override
    @SneakyThrows
    public void register(ServiceMetaData registryMetaData) {
        Lease leaseClient = client.getLeaseClient();
        long id = leaseClient.grant(3000L).get().getID();

        PutOption option = PutOption.builder().withLeaseId(id).build();

        KV kvClient = client.getKVClient();
        String path = root + registryMetaData.getServiceName()+ "/" + registryMetaData.getServiceAddress();
        ByteSequence key = ByteSequence.from(path, StandardCharsets.UTF_8);
        ByteSequence value = ByteSequence.from(JSONUtil.toJsonStr(registryMetaData), StandardCharsets.UTF_8);
        kvClient.put(key, value, option);
        System.out.println("注册etcd成功");

        localRegisterNodeKeySet.add(path);
    }

    @Override
    public void unRegister(ServiceMetaData registryMetaData) {
        KV kvClient = client.getKVClient();
        String path = root + registryMetaData.getServiceName() + "/" + registryMetaData.getServiceAddress();
        ByteSequence key = ByteSequence.from(path, StandardCharsets.UTF_8);
        kvClient.delete(key);

        localRegisterNodeKeySet.remove(path);
    }

    @SneakyThrows
    @Override
    public List<ServiceMetaData> getServiceAddress(String serviceName) {
        KV kvClient = client.getKVClient();
        String path = root + serviceName + "/";
        ByteSequence key = ByteSequence.from(path, StandardCharsets.UTF_8);
        GetOption option = GetOption.builder()
                .isPrefix(true)
                .build();
        return kvClient.get(key, option).get().getKvs()
                .stream().map(e -> JSONUtil.toBean(e.getValue().toString(StandardCharsets.UTF_8), ServiceMetaData.class))
                .collect(Collectors.toList());
    }

    @Override
    public void heartbeat() {
        CronUtil.schedule("*/10 * * * * *", new Task() {
            @Override
            public void execute() {
                localRegisterNodeKeySet.forEach(e -> {
                    try{
                        KV kvClient = client.getKVClient();
                        List<KeyValue> kvs = kvClient.get(ByteSequence.from(e, StandardCharsets.UTF_8)).get().getKvs();
                        if(CollectionUtil.isEmpty(kvs)){
                            return;
                        }
                        KeyValue keyValue = kvs.get(0);
                        String value = keyValue.getValue().toString(StandardCharsets.UTF_8);
                        ServiceMetaData serviceMetaData = JSONUtil.toBean(value, ServiceMetaData.class);
                        register(serviceMetaData);
                    }catch (Exception exception){
                        exception.printStackTrace();
                    }
                });
                log.info("心跳检测完成");
            }
        });
    }
}
