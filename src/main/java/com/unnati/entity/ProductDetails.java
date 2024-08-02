package com.unnati.entity;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDetails {
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long Productid;
	private String productName;
	@Column(length = 2000000000)
	private String image;
	
	private String description;
	
	private String brand;
	private double price;
	
	@Column(name="stock")
	private long quantity;
	
	@Column(unique = true) 
    private String modelNumber; 
	
	private String type;
	
    private boolean outOfStock;
    
    private Date localDate;
    @PrePersist
    protected void onCreate() {
        localDate = new Date();
    }
	@Override
	public String toString() {
		return "ProductDetails [Productid=" + Productid + ", productName=" + productName + ", image=" + image
				+ ", description=" + description + ", brand=" + brand + ", price=" + price + ", quantity=" + quantity
				+ ", modelNumber=" + modelNumber + ", type=" + type + ", outOfStock=" + outOfStock + ", localDate="
				+ localDate + "]";
	}
	
    
    

    
	
	
	

}
