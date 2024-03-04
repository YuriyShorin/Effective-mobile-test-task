package ru.effectivemobile.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Entity
@Table(name = "Accounts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Account {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private UUID id;

    @Column(name = "user_id")
    private UUID userId;

    @Column(name = "initial_deposit")
    private Double initialDeposit;

    @Column(name = "balance")
    private Double balance;

    public Account(UUID userId, Double initialDeposit, Double balance) {
        this.userId = userId;
        this.initialDeposit = initialDeposit;
        this.balance = balance;
    }
}
