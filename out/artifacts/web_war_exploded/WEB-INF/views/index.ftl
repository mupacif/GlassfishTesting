<#import "layout.ftl" as layout>
<@layout.layout>
    <h1>Bienvenue sur Interzon.be !</h1>
    <table>

        <#list productList as product>
            <tr>
                <td>
                    <img height="100" src="${product.imageUrl}">
                </td>
                <td>
                    <a href="product/${product.id}">${product.name}</a>
                </td>
            </tr>
        </#list>
    </table>
    <#if (productList?size == 0)>
    <p>Pas de produits, revenez plus tard !</p>
    </#if>

    <form action="" method="POST">
        <input type="text" name="name" id="name" placeholder="name">
        <textarea name="description" id="description" cols="30" rows="10" placeholder="description"></textarea>
        <input type="text" name="url" id="url" placeholder="url">
        <input type="number" name="price" placeholder="price">
        <input type="submit" value="enregrister">
    </form>

</@layout.layout>
