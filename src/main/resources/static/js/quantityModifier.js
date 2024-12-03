function updateQuantity(rowId, delta) {
    console.log('Function called with rowId:', rowId, 'delta:', delta);

    const quantitySpan = document.getElementById(`quantity-${rowId}`);
    const saveButton = document.getElementById(`save-${rowId}`);
    const quantityInput = document.getElementById(`quantity-input-${rowId}`);
    const decrementButton = document.getElementById(`decrement-${rowId}`);

    if (quantitySpan) {
        // Parse the current quantity, update it, and enforce minimum value of 1
        let currentQuantity = parseInt(quantitySpan.textContent, 10);
        currentQuantity = Math.max(1, currentQuantity + delta);

        // Update the displayed quantity
        quantitySpan.textContent = currentQuantity;

        // Update the hidden input value
        if (quantityInput) {
            quantityInput.value = currentQuantity;
        }

        // Mark as modified
        quantitySpan.style.color = 'red';
        if (saveButton) {
            saveButton.disabled = false;
        }

        // Enable or disable the decrement button
        if (decrementButton) {
            decrementButton.disabled = currentQuantity <= 1;
        }

        console.log('Quantity updated:', currentQuantity);
    }
}
