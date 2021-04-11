package com.javacuckoo.grpcunary.server;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.google.common.util.concurrent.Uninterruptibles;
import com.javacuckoo.models.Balance;
import com.javacuckoo.models.BalanceCheckRequest;
import com.javacuckoo.models.BankServiceGrpc;
import com.javacuckoo.models.WithdrawRequest;
import com.javacuckoo.models.BankServiceGrpc.BankServiceBlockingStub;
import com.javacuckoo.models.BankServiceGrpc.BankServiceStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankServiceTest {
	
	private BankServiceBlockingStub newBlockingStub;
	
	private BankServiceStub  bankServiceStub;
	
	@BeforeAll
	public void setup()  {
		ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
		this.newBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
		this.bankServiceStub = BankServiceGrpc.newStub(managedChannel);
		
	}

	@Test
	public void balanceTest() {
		BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder().setAccountNumber(11).build();
		Balance balance = this.newBlockingStub.getBalance(balanceCheckRequest);
		System.out.println("Received : "+balance.getAmount());
	}
	
	@Test
	public void withdrawTest() {
		WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(7).setAmount(40).build();
		
		this.newBlockingStub.withdraw(withdrawRequest).forEachRemaining(money -> System.out.println("Received : "+ money.getValue()));
		
	}
	
	@Test
	public void withdrawAsyncTest() throws InterruptedException {
		
		CountDownLatch countDownLatch = new  CountDownLatch(1);
		WithdrawRequest withdrawRequest = WithdrawRequest.newBuilder().setAccountNumber(10).setAmount(50).build();
		
		
		this.bankServiceStub.withdraw(withdrawRequest, new MoneyStreamingResponse(countDownLatch));
		countDownLatch.await();
		
	}

}
