(function () {
    var STORAGE_KEY = "sweetBakeryTheme";
    var toggle = document.getElementById("themeToggle");
    if (!toggle) return;

    function applyTheme(theme) {
        document.documentElement.setAttribute("data-theme", theme);
        toggle.setAttribute("aria-checked", theme === "dark" ? "true" : "false");
        toggle.setAttribute("aria-label", theme === "dark" ? "Chuyển sang chế độ sáng" : "Chuyển sang chế độ tối");
    }

    applyTheme(document.documentElement.getAttribute("data-theme") || "light");

    toggle.addEventListener("click", function () {
        var next = document.documentElement.getAttribute("data-theme") === "dark" ? "light" : "dark";
        try {
            localStorage.setItem(STORAGE_KEY, next);
        } catch (e) {}
        applyTheme(next);
    });
})();

function mediaUrl(media) {
    if (!media) return null;
    if (media.url) return media.url;
    if (media.urlOrNull) return media.urlOrNull;
    if (media.name) return "/api/v1/media/" + media.name;
    return null;
}

function updateCartCount() {
    return fetch("/api/v1/shopping-cart/product-count")
        .then(function (response) {
            if (!response.ok) throw new Error("Failed to load cart count");
            return response.json();
        })
        .then(function (data) {
            var badge = document.getElementById("cartCount");
            if (!badge) return;
            var count = data.count || 0;
            badge.textContent = String(count);
            badge.hidden = count === 0;
        });
}

function updateCartCountBadge() {
    return updateCartCount();
}
