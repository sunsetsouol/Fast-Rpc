package shop.sunsetsouol.registry;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RegistryEnum {
    ETCD("etcd"),
    ZOOKEEPER("zookeeper");

    private String type;
}
