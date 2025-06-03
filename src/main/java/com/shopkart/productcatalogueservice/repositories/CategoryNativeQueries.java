package com.shopkart.productcatalogueservice.repositories;

public interface CategoryNativeQueries {
    String findAllActiveCategories="SELECT c.id,c.name,c.description FROM categories c " +
            "WHERE c.state=:state";

    String findCategoryWithFeaturedAndAllProducts="SELECT \n" +
            "    c.id AS categoryId, c.name AS categoryName, c.description AS categoryDescription,\n" +
            "    fp.id AS featuredProductId, fp.name AS featuredProductName, p.id AS productId,\n" +
            "    p.name AS productName FROM categories c LEFT JOIN categories_featured_products cfp ON cfp.categories_id = c.id\n" +
            "\tLEFT JOIN products fp ON fp.id = cfp.featured_products_id LEFT JOIN products p ON p.category_id = c.id \n" +
            "    WHERE c.id = 1";
}
