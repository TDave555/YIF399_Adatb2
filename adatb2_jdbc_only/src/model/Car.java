package model;

public class Car {
	private int id;
	private String manufacturer;
	private String color;
	private int price;
	
	public Car(int id, String manufacturer, String color, int price) {
		super();
		this.id = id;
		this.manufacturer = manufacturer;
		this.color = color;
		this.price = price;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getManufacturer() {
		return manufacturer;
	}

	public void setManufacturer(String manufacturer) {
		this.manufacturer = manufacturer;
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	@Override
	public String toString() {
		return "Car [id=" + id + ", manufacturer=" + manufacturer + ", color=" + color + ", price=" + price + "]\n";
	}

}
