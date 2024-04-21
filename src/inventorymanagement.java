
	import java.util.Scanner;
	public class inventorymanagement {
		// It is the entry point for our program. JVM starts executing the code.
		public static void main(String[] args) {
			System.out.println(
					"Welcome the our Inventory Management Program.\nIn this program you will be able to make changes about the inventory.\nAlso you can see the price to sell the product on offer.");
	    //operator is an object defined in the manager class to use methods.
			Manager operator = new Manager();
			// With 'run' method, user can make selections from the menu
			operator.run();
		}
	}

	//first seperated class from main. This is to process all methods below inside the class.
	class Goods {
		// Goods has a default identifier which provides the system to be accessed from
		// another class.
		private String name;
		private int quantityOfProducts;
		private int qualityOfProducts;
		private double priceOfProducts;
		// 'private' means that these variables are only accessed within the same class.
		// This creates a constructor for the Goods class. It is marked as public,
		// meaning it can be accessed from outside the class.

		public Goods(String name, int quantity, double price, int quality) {
			this.quantityOfProducts = quantity;
			this.priceOfProducts = price;
			this.name = name;
			this.qualityOfProducts = quality;
		}

		// These are the methods that we created the products to specificate.

		public int getQuantity() {
			return quantityOfProducts;
		}

		public void setQuantity(int quantity) {
			this.quantityOfProducts = quantity;
		}

		public void getQuality(int quality) {
			this.qualityOfProducts = quality;
		}

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setPrice(double price) {
			this.priceOfProducts = price;
		}

		public double getPrice() {
			return priceOfProducts;
		}

		@Override
		public String toString() {
			return " Name: " + name + " Quantity: " + quantityOfProducts + " Quality: " + qualityOfProducts + " Price: $"
					+ priceOfProducts;
		}
	}

	//another class we created to process all the methods below 
	class Inventory {
		// MAX_SECTION_NUMBER and MAX_GOODS_PER_SECTION are constant variable defined in
		// Inventory class.
		public static final int MAX_GOODS_PER_SECTION = 100;
		public static final int MAX_SECTION_NUMBER = 6;

		private Goods[][] products;
		// we set it as private identifier to use just in this class.

		public Goods[][] getProducts() {
			return products;
		}

		private int[] countsOfProducts;

		public int[] getProductCounts() {
			return countsOfProducts;
		}

		public Inventory() {
			this.products = new Goods[MAX_SECTION_NUMBER][MAX_GOODS_PER_SECTION];
			this.countsOfProducts = new int[MAX_SECTION_NUMBER];
	//Constructor method of this class.
		}
	//This method is useful to see current inventory activity.
		public void displayInventory() {
			System.out.println("Current Inventory:");
			//This outer loop represents each section
			for (int section = 1; section < MAX_SECTION_NUMBER; section++) {
				System.out.println("Section " + section + ":");
				//This inner loop represents each product
				for (int i = 0; i < MAX_GOODS_PER_SECTION; i++) {
					Goods product = products[section][i];
					if (product != null)
						System.out.println(product);
				}
			}

		}
	//The products that we added should be in different sections
		public void addProduct(int sectionNumber, Goods product) {
			if (sectionNumber > 0 && sectionNumber < MAX_SECTION_NUMBER) {
				for (int i = 0; i < MAX_GOODS_PER_SECTION; i++)
					//it checks whether there is already a product.
					if (products[sectionNumber][i] == null) {
					products[sectionNumber][i] = product;//assigns the new product in inventory
						countsOfProducts[sectionNumber]++;
						System.out.println("Product added to Section " + sectionNumber + ".");
						return;
					}
				System.out.println("Section " + sectionNumber + " is full. We cannot add any products.");
			} else
				System.out.println("You entered an invalid choise. Section number should be between 1 and "+ (MAX_SECTION_NUMBER - 1) + ".");
		}
	}

	//Manager is the "manager and operation center of the system"  at all. 
	class Manager {
		 private Scanner scanner;
		 private Inventory inventoryState;

		// Manager is a constructor method to initialize the state of an object.
		public Manager() {
			this.scanner = new Scanner(System.in);
			this.inventoryState = new Inventory();
		}

		private void displayIntroduction() {
			System.out.println(
					"\nOptions:\n1. New Product\n2. Updating Product Quantity\n3. Removing Product (all amount of that product)\n4. Display Inventory");
			// this method displays the main menu.
		}

	//this run method can be the most important method to maintain the processes.
		public void run() {
			while (true) {
				displayIntroduction();

				System.out.print("Enter your choice: ");
				int choice1 = scanner.nextInt();

				switch (choice1) {
				case 1:
					addProduct();
					break;
				case 2:
					updateProduct();
					break;
				case 3:
					removeProduct();
					break;
				case 4:
					inventoryState.displayInventory();
					break;	
				default:
					System.out.println("Invalid choice. Try again.");
				}
			}
		}
	//Products are added in specified section
		private void addProduct() {
			Goods product = addingProduct();
			System.out.print("Enter the section number to add the product (1-5): ");
			int section = scanner.nextInt();
			inventoryState.addProduct(section, product);
		}
	//this part of code is used for updating the quantity of specified product.
		private void updateProduct() {
		    System.out.print("Enter the name of the product you'd like to update quantity: ");
		    String name_product = scanner.next();

		    System.out.print("Enter the new quantity: ");
		    int newQuantity = scanner.nextInt();

		    boolean productIsFound = false;

		    for (int section = 1; section < Inventory.MAX_SECTION_NUMBER; section++) {
		        // The loop is a logic to manipulate products within the inventory.
		        for (int i = 0; i < Inventory.MAX_GOODS_PER_SECTION; i++) {
		            Goods product = inventoryState.getProducts()[section][i];

		            if (product != null && product.getName().equalsIgnoreCase(name_product)) {
		                product.setQuantity(newQuantity);
		                System.out.println("Product quantity is updated.");
		                productIsFound = true;
		                break;
		            }
		        }
		        if (productIsFound) {
		            break;
		        }
		    }
		    // We should check if the product exists
		    if (!productIsFound) {
		        System.out.println("Product not found with name: " + name_product);
		    }
		}

	//this method to remove the goods presents in inventory.
		private void removeProduct() {
		    System.out.print("Enter the name of the product you'd like to remove all quantities of the product from its section: ");
		    String nameRemoveP = scanner.next();

		    boolean productIsFound = false;

		    for (int section = 0; section < Inventory.MAX_SECTION_NUMBER; section++) {
		        for (int i = 0; i < Inventory.MAX_GOODS_PER_SECTION; i++) {
		            Goods product = inventoryState.getProducts()[section][i];
		            //Checks if it is not a null and the name matches
		            if (product != null && product.getName().equalsIgnoreCase(nameRemoveP)) {
		                productIsFound = true;

		                //This part of the code is filling the gap and shifting the products.
		                if (i < Inventory.MAX_GOODS_PER_SECTION - 1) {
		                    System.arraycopy(
		                            inventoryState.getProducts()[section], i + 1,
		                            inventoryState.getProducts()[section], i,
		                            Inventory.MAX_GOODS_PER_SECTION - i - 1
		                    );
		                }
	                    //This part of the code removing product from the inventory
		                inventoryState.getProducts()[section][Inventory.MAX_GOODS_PER_SECTION - 1] = null;

		                inventoryState.getProductCounts()[section]--;

		                System.out.println("Product removed successfully.");
		                break;
		            }
		        }

		        if (productIsFound) {
		            break;
		        }
		    }

		    if (!productIsFound) {
		        System.out.println("Product is not found with this name: " + nameRemoveP);
		    }
		}
		private void aQuality() {
			System.out.print("Enter the quality of this product (1-10): ");
		}

		// In this part of code,we get inputs from use
		private Goods addingProduct() {

			System.out.print("Enter the name of the new product: ");
			scanner.nextLine();
			String name = scanner.nextLine();

			System.out.print("Enter the quantity of this product: ");
			int quantity = scanner.nextInt();

			int quality;

			do {
			    System.out.print("Enter the quality of this product (1-10): ");
			    quality = scanner.nextInt();

			    if (quality < 1 || quality > 10) {
			        System.out.println("Invalid value. Remember that quality has to be between 1 and 10.");
			    }
			    
			} while (quality < 1 || quality > 10);

				System.out.print("Enter the cost of this products (numerically in dollars $): ");
				double price = scanner.nextDouble();
				price *= quality;
				price /= 5;
				price *= (Math.log(quality + 1) / Math.log(6));
	            //This calculation is about the price choosing method respect to quality in industrial engineering.
				//When quality increases,the price to sell automatically increases.
				System.out.println("The price to sell this products would be " + price);
				
				return new Goods(name, quantity, price, quality);
			}
		}

}
