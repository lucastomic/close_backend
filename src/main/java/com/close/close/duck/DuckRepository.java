package com.close.close.duck;

import org.springframework.data.jpa.repository.JpaRepository;

/**
 * DuckRepository is the duck's implementation of the repository pattern.
 * In charge of all the interactions with the DB.
 */
public interface DuckRepository extends JpaRepository<Duck, DuckId> {
}
