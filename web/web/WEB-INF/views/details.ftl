<#import "layout.ftl" as layout>
<@layout.layout>
    <h2>${product.name}</h2>
    <img src="${product.imageUrl}">
    <p>${product.description}</p>
    <p>${product.price / 100} €</p>
    <div>
        <form action="" method="post">
            <input type="hidden" name="id" value="${product.id}">
            <input type="submit" value="Commander">
        </form>
    </div>
</@layout.layout>
