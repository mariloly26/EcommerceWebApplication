window.addEventListener('DOMContentLoaded', (event) => {
    document.getElementsByClassName("topnav")[0].innerHTML = 
        '<a href="DisplayProducts">Home</a>\n' +   //calls Mari's servlet
        '<a href="index.html">About Us</a>\n' + '<a href="Checkout">Cart</a>'+
        '<div id="search-wrapper">\n' +
            '<input id="search-bar" type="text" placeholder="Search.." onkeyup=fetchSearchResults()>\n' + 
            '<table id="search-table"></table>\n' +
        '</div>';
});

function renderSearchList() {
    var xml = String(this.responseText);
    parser = new DOMParser();
    xmlDoc = parser.parseFromString(xml,"text/xml");
    xmlDoc = xmlDoc.getElementsByTagName("product");
    products = [];
    for (i = 0; i < xmlDoc.length; i++) {
        var id = xmlDoc[i].getElementsByTagName("id")[0].innerHTML;
        var name = xmlDoc[i].getElementsByTagName("name")[0].innerHTML;
        var imagepath = xmlDoc[i].getElementsByTagName("imagepath")[0].innerHTML;
        products.push(new Product(id, name, imagepath));
    }
    results = new SearchResult(products);

    document.getElementById("search-table").innerHTML = results.renderProducts();
}

function hideResults() {
    console.log(document.getElementsByClassName("search-row"));
}

function fetchSearchResults() {
    var text = document.getElementById("search-bar").value;
    if (text.length == 0) return clearResults();
    var searchurl = "http://circinus-10.ics.uci.edu:8081/search.php?q=" + text;
    var xmlReq = new XMLHttpRequest();
    xmlReq.addEventListener("load", renderSearchList);
    xmlReq.open("GET", searchurl);
    xmlReq.send();
}

function clearResults() {
    document.getElementById("search-table").innerHTML = "";
}

class Product {
    constructor(productID, productName, imageURL) {
        this.productID = productID;
        this.productName = productName;
        this.imageURL = imageURL;
    }

    renderProduct() {
        return "" +
            '<tr class="search-row">\n' +
                '<td>\n' +
                    `<a href="product.html?id=${this.productID}">\n` + 
                        '<div class="search-item">\n' +
                            `<img src = ${this.imageURL} alt = "" >\n` +
                            '<span>' + this.productName + '</span>\n' +
                        '</div>\n' +
                    '</a>\n' +    
                '</td>\n' +
            '</tr>\n';
    }
}

class SearchResult {
    constructor(products) {
        this.products = products;
    }
    
    renderProducts() {
        var innerHTML = "";
        if (this.products.length == 0) return "";
        products.forEach(element => {
            innerHTML += element.renderProduct();
        })
        return innerHTML;
    }
};

