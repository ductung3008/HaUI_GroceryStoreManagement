package controller;

import java.io.IOException;
import java.util.List;

import model.Product;
import dao.ProductDAO;
import view.ProductManagementView;

public class ProductController {
	private ProductDAO productDAO;
	private ProductManagementView pmv;

	public ProductController(ProductDAO productDAO, ProductManagementView pmv) {
		this.productDAO = productDAO;
		this.pmv = pmv;
	}

	public List<Product> getAllProducts() throws ClassNotFoundException, IOException {
		return productDAO.getAll();
	}

	public Product getProductById(int id) throws ClassNotFoundException, IOException {
		return productDAO.get(Product -> Product.getId() == id);
	}

	public boolean addProduct(Product Product) throws ClassNotFoundException, IOException {
		if (!productDAO.add(Product)) {
			return false;
		}
		pmv.updateProductTable(getAllProducts());
		return true;
	}

	public boolean updateProduct(Product Product) throws ClassNotFoundException, IOException {
		if (!productDAO.update(Product)) {
			return false;
		}
		pmv.updateProductTable(getAllProducts());
		return true;
	}

	public boolean deleteProduct(Product Product) throws ClassNotFoundException, IOException {
		if (!productDAO.delete(Product)) {
			return false;
		}
		pmv.updateProductTable(getAllProducts());
		return true;
	}

	public void searchProducts(String name) throws ClassNotFoundException, IOException {
		List<Product> products = productDAO.searchByName(name);
		pmv.updateProductTable(products);
	}
}
