function handleSortChange(selectElement) {
    const form = selectElement.form;
    if (selectElement.value === "") {
        // Remove the "sort" parameter by setting its input to null
        const sortInput = form.querySelector('input[name="sort"]');
        if (sortInput) {
            sortInput.remove();
        }
    }
    form.submit();
}