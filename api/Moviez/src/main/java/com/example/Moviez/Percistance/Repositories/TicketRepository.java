package com.example.Moviez.Percistance.Repositories;



import com.example.Moviez.Percistance.Entities.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket, Long> {

    // Spring Data JPA automatically writes the SQL to find tickets by the related Showing's ID
    List<Ticket> findByShowing_ShowingId(Long showingId);

    List<Ticket> findByUser_KeycloakId(String keycloakId);

    List<Ticket> findByPayuOrderId(String payuOrderId);


}
