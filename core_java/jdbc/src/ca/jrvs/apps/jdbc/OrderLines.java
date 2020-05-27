package ca.jrvs.apps.jdbc;

import java.math.BigDecimal;

public class OrderLines {
  private long quantity;
  private String productCode;
  private String productName;
  private long productSize;
  private String productVariety;
  private BigDecimal productPrice;

  @Override
  public String toString() {
    return "OrderLines{" +
        "quantity=" + quantity +
        ", productCode='" + productCode + '\'' +
        ", productName='" + productName + '\'' +
        ", productSize=" + productSize +
        ", productVariety='" + productVariety + '\'' +
        ", productPrice=" + productPrice +
        '}';
  }

  public long getQuantity() {
    return quantity;
  }

  public void setQuantity(long quantity) {
    this.quantity = quantity;
  }

  public String getProductCode() {
    return productCode;
  }

  public void setProductCode(String productCode) {
    this.productCode = productCode;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public long getProductSize() {
    return productSize;
  }

  public void setProductSize(long productSize) {
    this.productSize = productSize;
  }

  public String getProductVariety() {
    return productVariety;
  }

  public void setProductVariety(String productVariety) {
    this.productVariety = productVariety;
  }

  public BigDecimal getProductPrice() {
    return productPrice;
  }

  public void setProductPrice(BigDecimal productPrice) {
    this.productPrice = productPrice;
  }
}

