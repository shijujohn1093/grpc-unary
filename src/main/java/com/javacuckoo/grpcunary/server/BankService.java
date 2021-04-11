package com.javacuckoo.grpcunary.server;

import com.javacuckoo.models.Balance;
import com.javacuckoo.models.BalanceCheckRequest;
import com.javacuckoo.models.BankServiceGrpc.BankServiceImplBase;

import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceImplBase{

	@Override
	public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
		int accountId = request.getAccountNumber();
		Balance balance = Balance.newBuilder().setAmount(AccountDatabase.getBalance(accountId)).build();
		responseObserver.onNext(balance);
		responseObserver.onCompleted();
	}

}
