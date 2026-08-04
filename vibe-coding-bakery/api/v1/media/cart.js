var SweetHomeBakeryCart = (function () {
    var STORAGE_KEY = "cart";
    var ORDERS_STORAGE_KEY = "orders";
    var UPDATED_EVENT = "sweethomebakery:cart-updated";

    function readCart() {
        try {
            var raw = localStorage.getItem(STORAGE_KEY);
            return raw ? JSON.parse(raw) : [];
        } catch (e) {
            return [];
        }
    }

    function notifyUpdated() {
        try {
            window.dispatchEvent(new CustomEvent(UPDATED_EVENT));
        } catch (e) {}
        renderBadge();
    }

    function writeCart(cart) {
        try {
            localStorage.setItem(STORAGE_KEY, JSON.stringify(cart));
        } catch (e) {}
        notifyUpdated();
    }

    function addItem(item) {
        var cart = readCart();
        var existing = null;
        for (var i = 0; i < cart.length; i++) {
            if (cart[i].name === item.name && cart[i].sizeKey === item.sizeKey) {
                existing = cart[i];
                break;
            }
        }
        if (existing) {
            existing.quantity += item.quantity;
        } else {
            cart.push(item);
        }
        writeCart(cart);
    }

    function updateQuantity(index, quantity) {
        var cart = readCart();
        if (!cart[index]) return;
        if (quantity <= 0) {
            cart.splice(index, 1);
        } else {
            cart[index].quantity = quantity;
        }
        writeCart(cart);
    }

    function removeItem(index) {
        var cart = readCart();
        if (!cart[index]) return;
        cart.splice(index, 1);
        writeCart(cart);
    }

    function getTotalQuantity() {
        var cart = readCart();
        var total = 0;
        for (var i = 0; i < cart.length; i++) {
            total += cart[i].quantity;
        }
        return total;
    }

    function getTotalPrice() {
        var cart = readCart();
        var total = 0;
        for (var i = 0; i < cart.length; i++) {
            total += cart[i].unitPrice * cart[i].quantity;
        }
        return total;
    }

    function clearCart() {
        writeCart([]);
    }

    function formatPrice(amount) {
        return String(amount).replace(/\B(?=(\d{3})+(?!\d))/g, ".") + "đ";
    }

    function generateOrderCode() {
        var now = new Date();
        var stamp = now.getFullYear().toString() +
            String(now.getMonth() + 1).padStart(2, "0") +
            String(now.getDate()).padStart(2, "0") +
            String(now.getHours()).padStart(2, "0") +
            String(now.getMinutes()).padStart(2, "0") +
            String(now.getSeconds()).padStart(2, "0");
        var random = Math.floor(1000 + Math.random() * 9000);
        return "DH" + stamp + random;
    }

    function readOrders() {
        try {
            var raw = localStorage.getItem(ORDERS_STORAGE_KEY);
            return raw ? JSON.parse(raw) : [];
        } catch (e) {
            return [];
        }
    }

    function saveOrder(order) {
        var orders = readOrders();
        orders.push(order);
        try {
            localStorage.setItem(ORDERS_STORAGE_KEY, JSON.stringify(orders));
        } catch (e) {}
        return order;
    }

    function getOrder(orderCode) {
        var orders = readOrders();
        for (var i = 0; i < orders.length; i++) {
            if (orders[i].orderCode === orderCode) return orders[i];
        }
        return null;
    }

    function renderBadge() {
        var badge = document.getElementById("cartCount");
        if (!badge) return;
        var total = getTotalQuantity();
        badge.textContent = String(total);
        badge.hidden = total === 0;
    }

    function init() {
        renderBadge();
        window.addEventListener(UPDATED_EVENT, renderBadge);
        window.addEventListener("storage", function (event) {
            if (event.key === STORAGE_KEY) renderBadge();
        });
    }

    if (document.readyState === "loading") {
        document.addEventListener("DOMContentLoaded", init);
    } else {
        init();
    }

    return {
        readCart: readCart,
        writeCart: writeCart,
        addItem: addItem,
        updateQuantity: updateQuantity,
        removeItem: removeItem,
        clearCart: clearCart,
        getTotalQuantity: getTotalQuantity,
        getTotalPrice: getTotalPrice,
        formatPrice: formatPrice,
        renderBadge: renderBadge,
        generateOrderCode: generateOrderCode,
        saveOrder: saveOrder,
        getOrder: getOrder
    };
})();
