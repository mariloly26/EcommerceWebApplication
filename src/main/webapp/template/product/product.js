//const query = new URLSearchParams(window.location.search);
//const server_url = 'http://localhost:8080/testservlet/'; //LOCALHOST FOR TESTING THAT IMAGES WORK
//                                                          //CHANGE TO CIRCINUS URL WHEN DEPLOYED
//
////returns item id
//function getID(){
//    return query.get("id");
//}
//
//// function loadJSONFile(fileName, callback){
////     var xml = new XMLHttpRequest();
////     xml.open('GET', fileName, true);
////     xml.overrideMimeType("application/json");
////     xml.onreadystatechange = function() {
////         if (xml.readyState==4 && xml.status==200) {
////             callback(xml.responseText);
////         }
////     };
////     xml.send(null);
//// }
//
//var images = [];
//var currImage = 0;
////testing new productInfo
//function productInfo(){
//    if (window.XMLHttpRequest) {
//        var xhr = new XMLHttpRequest();
//    } else {
//        var xhr = new ActiveXObject("Microsoft.XMLHTTP");
//    }
//    xhr.onreadystatechange = function () {
//        if (xhr.readyState == 4 && xhr.status == 200) {
//            var result = xhr.responseText;
//            console.log(result);
//            result = JSON.parse(result);
//            console.log(result['images']);
//            images = result['images'];
//            document.getElementById("pic").src = server_url + images[0];
//            document.getElementById("name").innerHTML = result['name'];
//            document.getElementById("price").innerHTML = result['price'];
//            document.getElementById("material").innerHTML = "Material: " + result['material'] + '; ';
//            document.getElementById("setsize").innerHTML = "Quantity: " + result['set_quantity']; + '; '
//            document.getElementById("manufacturer").innerHTML = "Manufacturer: " + result['manufacturer'];
//            document.getElementById("ProductName").value = result['name'];
//          
//            update_totals();
//        }
//    };
//
//    xhr.open("GET", "ProductInfo?id="+getID(), true);
//    xhr.send(null);
//}
//
//
//function changeImage(){
//    if (currImage == (images.length-1)) {
//        currImage = 0;
//    } else {
//        currImage++;
//    }
//    document.getElementById("pic").src = server_url + images[currImage];
//}
//
//// ORDER FORM //
//
//function update_totals()
//{
//    // SUBTOTAL
//    var price = document.getElementById("price").innerHTML;
//    var quantity = document.getElementById("ProductQty").value;
//    var subtotal = (parseFloat(price) * quantity).toFixed(2);
//    document.getElementById("subtotal").innerHTML = subtotal;
//    
//
//    // TAX & ORDER_TOTAL
//    if (window.XMLHttpRequest) {
//        var xhr = new XMLHttpRequest();
//    } else {
//        var xhr = new ActiveXObject("Microsoft.XMLHTTP");
//    }
//    xhr.onreadystatechange = function () {
//        if (xhr.readyState == 4 && xhr.status == 200) {
//            var result = xhr.responseText;
//            var order_total = parseFloat(subtotal) + (parseFloat(subtotal)*parseFloat(result));
//            if (!isNaN(order_total))
//            {
//                document.getElementById("tax_rate").innerHTML = parseFloat(result)*100;
//                document.getElementById("tax").innerHTML = (parseFloat(subtotal)*parseFloat(result)).toFixed(2);
//                document.getElementById("order_total").innerHTML = order_total.toFixed(2);
//            }
//            else 
//            {
//                document.getElementById("order_total").innerHTML = subtotal;
//            }
//        }
//    };
//
//    xhr.open("GET", "product.php?zip="+document.getElementById("Zip").value, true);
//    xhr.send(null);
//}

function validName(name) {
    var valid_chars = /^[a-zA-Z0-9'\-]+( [a-zA-Z0-9'\-]+)*$/;
    return (name.match(valid_chars));
}

function validEmail(email) {
    atpos = email.indexOf("@");
    dotpos = email.lastIndexOf(".");
     
    if (atpos < 1 || ( dotpos - atpos < 2 )) 
    {
        return (false);
    }
    return (true) ;
}

function validPhone(phone) {
    var valid_num = /^\(?\d{3}\)?[ -]?\d{3}[- ]?\d{4}$/;
    return phone.match(valid_num);
}

function alphanumeric(text) {
    var alphanum = /^[0-9a-zA-Z]*$/;
    return text.match(alphanum);
}

function numeric(num) {
    var valid_num = /^[0-9]+$/;
    return num.match(valid_num);
}

function validateOrderForm() {
    err_str = "";
    console.log("validating");
    
//    var qty = document.order.ProductQty.value;
//    if (qty == "" || qty == 0) 
//        err_str += "Please enter a non-zero product quantity.\n";
    
    var fname = document.order.FirstName.value;
    if (fname == "")
    {
        err_str += "First Name field is empty.\n";
    }
    else if (!validName(fname))
    {
        err_str += "Please enter a valid first name.\n";
    }

    var lname = document.order.LastName.value;
    if (lname == "")
    {
        err_str += "Last Name field is empty.\n";
    }
    else if (!validName(lname))
    {
        err_str += "Please enter a valid last name.\n";
    }

    var email = document.order.Email.value;
    if (email == "")
    {
        err_str += "Email field is empty.\n";
    }
    else if (!validEmail(email))
    {
        err_str += "Please enter a valid email address.\n";
    }
        
    var phone = document.order.PhoneNum.value;
    if (phone == "")
    {
        err_str += "Phone number field is empty.\n";
    }
    else if (!validPhone(phone))
    {
        err_str += "Please enter a valid phone number.\n";
    }

    var address = document.order.Address.value;
    if (address == "")
    {
        err_str += "Address field is empty.\n";
    }
    
    var apt = document.order.Apartment.value;
    if (apt != "" && !alphanumeric(apt))
    {
        err_str += "Apt/Unit field must be alphanumeric.\n";
    }

    var city = document.order.City.value;
    if (city == "")
    {
        err_str += "City field is empty.\n";
    }
    else if (!validName(city))
    {
        err_str += "Please enter a valid city.\n";
    }

    var state_select = document.getElementById("State");
    var state = state_select.options[state_select.selectedIndex].value;
    if (state == "")
    {
        err_str += "Please select a state.\n"
    }

    var zip = document.order.Zip.value;
    if (zip == "")
    {
        err_str += "Zipcode field is empty.\n";
    }
    else if (!numeric(zip))
    {
        err_str += "Please enter a numeric zipcode.\n";
    }

    // PAYMENT INFORMATION

    var CCName = document.order.CCName.value;
    if (CCName == "")
    {
        err_str += "Name on Card field is empty.\n";
    }
    else if (!validName(CCName))
    {
        err_str += "Please enter a valid Name on Card.\n";
    }

    var CCNum = document.order.CCNum.value;
    CCNum = CCNum.replace(/\s+/g, '');
    if (CCNum == "")
    {
        err_str += "Credit Card Number field is empty.\n";
    }
    else if (!numeric(CCNum) || CCNum.length < 13 || CCNum.length > 19)
    {
        err_str += "Please enter a valid credit card number.\n";
    }

    var CVV = document.order.CVV.value;
    if (CVV == "")
    {
        err_str += "CVV field is empty.\n";
    }
    else if (!numeric(CVV) || CVV.length < 3 || CVV.length > 4)
    {
        err_str += "Please enter a valid CVV.\n";
    }

    var month_select = document.getElementById("ExpMonth");
    var month = month_select.options[month_select.selectedIndex].value;
    if (month == "")
    {
        err_str += "Please select an expiration month.\n"
    }

    var year_select = document.getElementById("ExpMonth");
    var year = year_select.options[year_select.selectedIndex].value;
    if (year == "")
    {
        err_str += "Please select an expiration year.\n"
    }

    if (err_str != "")
    {
        alert(err_str);
        return (false);
    }

//    var form = document.getElementById('order');
//    form.action = "Confirmation";
    return (true);
    
}
