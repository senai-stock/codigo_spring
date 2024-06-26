package app.senaistock.stock_senai.Model;


import java.io.Serializable;

import jakarta.persistence.*;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Salas implements Serializable{
    @Id
    private Long id_sala;
    private String nome_sala;
    
	@ManyToOne
    @JoinColumn(name = "idbloco", nullable = false)
    private Blocos idbloco;

	@ManyToOne
    @JoinColumn(name = "id_area", nullable = false)
    private Areas id_area;
}