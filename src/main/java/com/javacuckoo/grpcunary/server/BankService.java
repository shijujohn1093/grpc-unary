package com.javacuckoo.grpcunary.server;

import com.javacuckoo.models.Balance;
import com.javacuckoo.models.BalanceCheckRequest;
import com.javacuckoo.models.DepositRequest;
import com.javacuckoo.models.Money;
import com.javacuckoo.models.WithdrawRequest;
import com.javacuckoo.models.BankServiceGrpc.BankServiceImplBase;

import io.grpc.Status;
import io.grpc.stub.StreamObserver;

public class BankService extends BankServiceImplBase{

	@Override
	public void getBalance(BalanceCheckRequest request, StreamObserver<Balance> responseObserver) {
		int accountId = request.getAccountNumber();
		Balance balance = Balance.newBuilder().setAmount(AccountDatabase.getBalance(accountId)).build();
		responseObserver.onNext(balance);
		responseObserver.onCompleted();
	}

	@Override
	public void deposit(DepositRequest request, StreamObserver<Balance> responseObserver) {
		int accountId = request.getAccountNumber();
		int amount = request.getAmount();
		Balance balance = Balance.newBuilder().setAmount(AccountDatabase.addBalance(accountId, amount)).build();
		responseObserver.onNext(balance);
		responseObserver.onCompleted();		
	}
	
	@Override
	public void withdraw(WithdrawRequest request, StreamObserver<Money> responseObserver) {
		int accountId = request.getAccountNumber();
		int amount = request.getAmount();
		int balance  = AccountDatabase.getBalance(accountId);
		
		if(balance < amount) {
			Status status = Status.FAILED_PRECONDITION.withDescription("No Enough Money. You have only "+balance);
			responseObserver.onError(status.asRuntimeException());
			return;
		}
		

		for (int i = 0; i < (amount/10); i++) {
			Money money = Money.newBuilder().setValue(10).build();	
			responseObserver.onNext(money);
			System.out.println("Sent mooneyxx "+10);
			AccountDatabase.deductBalance(accountId, 10);		
		}	
		responseObserver.onCompleted();
	}
}
