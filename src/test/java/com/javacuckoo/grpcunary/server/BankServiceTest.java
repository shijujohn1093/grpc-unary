package com.javacuckoo.grpcunary.server;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import com.javacuckoo.models.Balance;
import com.javacuckoo.models.BalanceCheckRequest;
import com.javacuckoo.models.BankServiceGrpc;
import com.javacuckoo.models.BankServiceGrpc.BankServiceBlockingStub;

import io.grpc.ManagedChannel;
import io.grpc.ManagedChannelBuilder;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class BankServiceTest {
	
	private BankServiceBlockingStub newBlockingStub;
	
	
	@BeforeAll
	public void setup()  {
		ManagedChannel managedChannel = ManagedChannelBuilder.forAddress("localhost", 6565).usePlaintext().build();
		this.newBlockingStub = BankServiceGrpc.newBlockingStub(managedChannel);
		
	}

	@Test
	public void balanceTest() {
		BalanceCheckRequest balanceCheckRequest = BalanceCheckRequest.newBuilder().setAccountNumber(11).build();
		Balance balance = this.newBlockingStub.getBalance(balanceCheckRequest);
		System.out.println("Received : "+balance.getAmount());
	}

}
