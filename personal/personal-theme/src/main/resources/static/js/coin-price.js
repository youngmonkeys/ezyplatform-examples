$(function () {
    const widget = $('#coin-widget');

    $('#coin-widget-toggle').on('click', function () {
        widget.toggleClass('expanded');
    });

    $('#coin-widget-close').on('click', function () {
        widget.removeClass('expanded');
    });

    loadCoinPrices();
});

function renderPriceChange(priceChange) {
    if (!priceChange) {
        return '<span class="price-flat">—</span>';
    }

    if (priceChange.startsWith('-')) {
        return `
            <span class="price-down">
                <span class="price-arrow">↓ </span>${priceChange}
            </span>
        `;
    }

    if (priceChange === '0' || priceChange === '0.00') {
        return `
            <span class="price-flat">
                <span class="price-arrow">— </span>0
            </span>
        `;
    }

    return `
        <span class="price-up">
            <span class="price-arrow">↑ </span>${priceChange}
        </span>
    `;
}

function loadCoinPrices() {
    $.get('/api/v1/coins/latest', function (data) {
        console.log(data);
        const tbody = $('#coin-price-body');
        tbody.empty();

        data.forEach(function (coin) {
            const changeClass = coin.price_change >= 0 ? 'text-success' : 'text-danger';

            tbody.append(`
                <tr>
                    <td class="coin-info">
                        <span class="symbol">${coin.base_symbol}</span>
                        <span class="name">${coin.base_name}</span>
                    </td>
                    <td class="coin-price">${coin.price}</td>
                    <td class="${changeClass}">
                        ${renderPriceChange(coin.price_change)}%
                    </td>
                </tr>
            `);
        });
    });
}
