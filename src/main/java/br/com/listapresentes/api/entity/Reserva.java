package br.com.listapresentes.api.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "reservas")
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "item_id", nullable = false, unique = true)
    private Item item;

    @Column(name = "nome_convidado", nullable = false)
    private String nomeConvidado;

    @Column(name = "email_convidado")
    private String emailConvidado;

    @Column(name = "mensagem")
    private String mensagem;

    @Column(name = "data_reserva", nullable = false)
    @Builder.Default
    private LocalDateTime dataReserva = LocalDateTime.now();
}
