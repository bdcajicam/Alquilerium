package co.edu.unal.isii.client;

import javax.jdo.annotations.IdGeneratorStrategy;
import javax.jdo.annotations.IdentityType;
import javax.jdo.annotations.PersistenceCapable;
import javax.jdo.annotations.Persistent;
import javax.jdo.annotations.PrimaryKey;

@PersistenceCapable(identityType = IdentityType.APPLICATION)
public class Reserva {

	public Reserva() {}
	
	public long getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

//	public Date getFecha() {
//		return fecha;
//	}
//
//	public void setFecha(Date fecha) {
//		this.fecha = fecha;
//	}


	public Integer getId_cliente() {
		return id_cliente;
	}

	public void setId_cliente(Integer id_cliente) {
		this.id_cliente = id_cliente;
	}

	public String getId_prenda() {
		return id_prenda;
	}

	public void setId_prenda(String id_prenda) {
		this.id_prenda = id_prenda;
	}


	@PrimaryKey
	@Persistent(valueStrategy = IdGeneratorStrategy.IDENTITY) public long id;
	//public Date fecha;
	public Integer id_cliente;
	public String id_prenda;
	
	public Reserva(long id, Integer id_cliente, String id_prenda) {
		super();
		this.id = id;
		//this.fecha = new Date();
		this.id_cliente = id_cliente;
		this.id_prenda = id_prenda;
	}
	
	
}
