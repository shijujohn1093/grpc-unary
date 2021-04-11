package com.javacuckoo.grpcunary.server;

import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class AccountDatabase {
	
	/**
	 * This is DB
	 * 1 => 10
	 * 2 => 20
	 * ..
	 * 10 => 100
	 */
	
	private static final Map<Integer, Integer> balances = IntStream.rangeClosed(1, 10).boxed().collect(Collectors.toMap(Function.identity(), v -> v*10));
	
	
	public static int getBalance(int accountId) {
		return balances.get(accountId);
	}
	
	public static int addBalance(int accountId, int amount) {
		return balances.computeIfPresent(accountId, (k, v) -> v+amount);
	}
	
	public static int deductBalance(int accountId, int amount) {
		return balances.computeIfPresent(accountId, (k, v) -> v-amount);
	}

}
