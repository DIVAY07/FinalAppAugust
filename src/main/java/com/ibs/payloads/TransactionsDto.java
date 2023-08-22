package com.ibs.payloads;
import com.ibs.entities.Transactions;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Setter
@Getter
public class TransactionsDto {
@NotNull
private int payee;

@NotNull
private int receiver;

@NotNull
private String mode;

@NotNull
private int transId;


private int amount;

	
}
