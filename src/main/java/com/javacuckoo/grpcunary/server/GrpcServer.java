package com.javacuckoo.grpcunary.server;

import java.io.IOException;

import io.grpc.Server;
import io.grpc.ServerBuilder;

public class GrpcServer {

	public static void main(String[] args) throws IOException, InterruptedException {
		int port = 6565;
		Server server = ServerBuilder.forPort(6565).addService(new BankService()).build();
		
		server.start();
		System.out.println("Server listening on "+port);
		server.awaitTermination();
		
	}
}
