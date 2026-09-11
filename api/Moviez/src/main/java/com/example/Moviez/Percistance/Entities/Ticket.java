package com.example.Moviez.Percistance.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "Tickets")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Ticket {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ticketid")
    private Long ticketId;

    // Links to the Showing entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "showingid", nullable = false)
    private Showing showing;

    // Links to the User entity
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false)
    private User user;

    @Column(name = "sitnumber", nullable = false)
    private Integer sitNumber;

    @Column(name = "sitrow", nullable = false, length = 2)
    private String sitRow;

    @Column(name = "status", length = 20)
    private String status = "RESERVED"; // Default status before PayU payment completes

    @Column(name = "payu_order_id")
    private String payuOrderId;


    public Ticket(Showing showing, User user, Integer sitNumber, String sitRow) {
        this.showing = showing;
        this.user = user;
        this.sitNumber = sitNumber;
        this.sitRow = sitRow;

        this.status = "PENDING";
    }

}
