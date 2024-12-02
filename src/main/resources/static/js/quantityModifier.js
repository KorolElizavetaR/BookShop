function updateQuantity(orderId, delta) {
    console.log('Function called with rowId:', orderId, 'delta:', delta);

    const quantitySpan = document.getElementById(`quantity-${orderId}`);
    console.log('Quantity span:', quantitySpan);

    const saveButton = document.getElementById(`save-${orderId}`);
    console.log('Save button:', saveButton);

    if (quantitySpan) {
        console.log('Updating quantity...');
        let currentQuantity = parseInt(quantitySpan.textContent, 10);
        console.log('Current quantity:', currentQuantity);

        currentQuantity = Math.max(1, currentQuantity + delta);
        quantitySpan.textContent = currentQuantity;

        quantitySpan.style.color = 'red';
        if (saveButton) {
            saveButton.disabled = false;
        }
        console.log('Quantity updated:', currentQuantity);
    }
}
