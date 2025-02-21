package shop.sunsetsouol.server;

import shop.sunsetsouol.model.RpcRequest;
import shop.sunsetsouol.model.RpcResponse;
import shop.sunsetsouol.model.ServiceMetaData;

import java.util.concurrent.ExecutionException;

public interface RpcClient {

    RpcResponse doRequest(RpcRequest request, ServiceMetaData serviceMetaData) throws ExecutionException, InterruptedException;
}
