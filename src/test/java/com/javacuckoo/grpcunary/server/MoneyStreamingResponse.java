package com.javacuckoo.grpcunary.server;

import java.util.concurrent.CountDownLatch;

import com.javacuckoo.models.Money;

import io.grpc.stub.StreamObserver;

public class MoneyStreamingResponse implements StreamObserver<Money>{
	private CountDownLatch countDownLatch;
	
	public MoneyStreamingResponse (CountDownLatch countDownLatch) {
		this.countDownLatch  =countDownLatch;
	}
	@Override
	public void onNext(Money value) {
		
		System.out.println("Received async : " + value.getValue());
	}

	@Override
	public void onError(Throwable t) {
		countDownLatch.countDown();
		System.out.println(t.getMessage());
		
	}

	@Override
	public void onCompleted() {
		countDownLatch.countDown();
		System.out.println("Server is Done !!");
		
	}

}
