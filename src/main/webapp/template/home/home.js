// let server_url = "http://circinus-10.ics.uci.edu:8080/project1/static/";

// create content
// function renderProduct(prodURL, imgURL, cat, name, price, mat, owner) {
//   // these classes help sort out which products belong to a category
//   //    and which need to be shown
//   var html = "<div class = 'product col-25 col-s-50 col-xs-100 " + cat + " show'>\n\t" +
//       "<div class = 'content'>\n\t\t" +
//           "<a href = '" + prodURL + "' >\n\t\t\t" +
//               "<img class='regular' src='" + imgURL + "' alt='" + name +
//               "' onmouseover='imageSize(this, 300)' onmouseout='imageSize(this, 250)'>\n\t\t" +
//           "</a>\n\t\t" +
//           "<h4>" + name + "</h4>\n\t\t" +
//           "<p>" + price + "</p>\n\t\t" +
//           "<p>" + mat + "</p>\n\t\t" +
//           "<p>" + owner + "</p>\n\t" +
//       "</div>\n" +
//   "</div>\n";
//   return html;
// }

function imageSize(img, value) {
    img.style.maxWidth = `${value}px`;
    img.style.maxHeight = `${value}px`;
}

function w3AddClass(element, name) {
  var i, arr1, arr2;
  arr1 = element.className.split(" ");
  arr2 = name.split(" ");
  for (i = 0; i < arr2.length; i++) {
    if (arr1.indexOf(arr2[i]) == -1) {element.className += " " + arr2[i];}
  }
}

function w3RemoveClass(element, name) {
  var i, arr1, arr2;
  arr1 = element.className.split(" ");
  arr2 = name.split(" ");
  for (i = 0; i < arr2.length; i++) {
    while (arr1.indexOf(arr2[i]) > -1) {
      arr1.splice(arr1.indexOf(arr2[i]), 1);
    }
  }
  element.className = arr1.join(" ");
}

function filterSelection(c) {
    var x, i;
    x = document.getElementsByClassName("product");
    if (c === "all") {
        c = "";
    }
    console.log(c);
    for (i = 0; i < x.length; i++) {
        w3RemoveClass(x[i], "show");
        w3RemoveClass(x[i], "hide");
        if (x[i].className.indexOf(c) > -1) {
            w3AddClass(x[i], "show");
        } else {
            w3AddClass(x[i], "hide");
        }
    }
}

// fetch
function fetchAndPerform(url, lambda) {
  return fetch(url)
      .then((resp)=>{
          return resp.json();
      })
      .then((json)=>{
          return lambda(json);
      }).catch((err)=>{
          console.log(err);
      });
}

// parse path directory to a query
// function pathToQuery(string) {
//   var match = string.match("(.*)\/(.*)\/");
//   return "product.html?cat=" + match[1] + "&item=" + match[2];
// }

// var insertProduct = (json)=>{
//   // target query looks like this: ?cat=dice&item=zinc_aqua
//   query = "";
//   try { // chance that query doesn't match e.g. match doesn't contain 3 values
//         query = pathToQuery(json["images"][0]);
//   } catch {
//         console.log("Malformed query", json["images"][0]);
//   } finally {
//         credit = (json["credit"]["publisher"]) ? "Publisher: " +
//             json["credit"]["publisher"] : "Manufacturer: " +
//                                       json["credit"]["manufacturer"];
//     return renderProduct(query, server_url + json["images"][0],
//                        json["category"], json["name"], json["price"],
//                        json["material"], credit);
//     }
// }

//access project1/static/products.json
// -> parse to get categories, locations of product_info.json's
/*    =>      
        {
          categories  = [dice, dice_bag, mat, book]
          info = [/dice/aqua/*.json, ...]
        }*/
//    -> generate divs for the columns per category, add one
//    -> generate div per info
//        -> add content div
// function getProductCategories(productJSON) {
//   var categories = [];
//   for (cat in productJSON["folders"]) {
//     categories.push(cat);
//   }
//   return categories;
// }

// function getProductInfos(productJSON, categories) {
//  subproducts = []
//  for (index in categories) {
//     for (prod in productJSON["folders"][categories[index]]) {
//       subproducts.push(categories[index] + "/" + productJSON["folders"][categories[index]][prod] +
//                       "/product_info.json");
//     }
//   }
//   return subproducts;
// }

// function getAllProducts(productJSON) {
//   categories = getProductCategories(productJSON);
//   return {
//     "category": categories,
//     "info": getProductInfos(productJSON, categories)
//   };
// }

// async function addAllProducts() {
//   fetchAndPerform(server_url + "products.json", getAllProducts)
//     .then((products)=>{
//       // generate all html components for each product_info available
//       renders = []
//       for (i in products["info"]) {
//         // fetch a product_info from the server and generate an html component
//         // these all come back as Promises
//         renders.push(fetchAndPerform(server_url + products["info"][i],
//                                     insertProduct));
//       }
//       // renders take a couple cycles to generate, wait for them
//       (async function() {
//         const results = await Promise.all(renders);
//         str = "";
//         for (i in results) {
//             str += results[i];
//         }
//         // add products to the row div
//         document.getElementsByClassName("row")[0].innerHTML = str;
//       })();
//     }).catch((err)=>console.log(err));
// }

window.addEventListener('DOMContentLoaded', (event) => {
    // Add active class to the current button (highlight it)
    var btnContainer = document.getElementById("myBtnContainer"),
        btns = btnContainer.getElementsByClassName("btn");
    for (var i = 0; i < btns.length; i++) {
        btns[i].addEventListener("click", function(){
            console.log("clicked");
            var current = document.getElementsByClassName("active");
            current[0].className = current[0].className.replace(" active", "");
            this.className += " active";
        });
    }

    // addAllProducts();
});
