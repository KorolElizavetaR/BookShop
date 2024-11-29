const totalPagesEndpoint = "/api/pagesCounter"; // Endpoint to fetch the total number of pages
let totalPages = 0; // Total pages fetched from the server
let currentPage = parseInt(new URLSearchParams(window.location.search).get("page")) || 1; // Get current page from query string or default to 1

const paginationControls = document.getElementById("pagination-controls");

// Fetch the total number of pages from the server
async function fetchTotalPages() {
    try {
        const response = await fetch(totalPagesEndpoint);
        if (!response.ok) {
            throw new Error("Failed to fetch total pages");
        }

        // Parse the response
        totalPages = parseInt(await response.text(), 10);

        // Ensure the current page is valid
        currentPage = Math.max(1, Math.min(currentPage, totalPages));

        // Render pagination controls
        renderPagination();
    } catch (error) {
        console.error("Error fetching total pages:", error);
    }
}

// Render pagination controls dynamically
function renderPagination() {
    paginationControls.innerHTML = ""; // Clear existing pagination controls

    // Create page number buttons
    for (let i = 1; i <= totalPages; i++) {
        const pageButton = document.createElement("div");
        pageButton.className = `page-item ${i === currentPage ? "active" : ""}`;
        pageButton.textContent = i;
        pageButton.addEventListener("click", () => changePage(i));
        paginationControls.appendChild(pageButton);
    }
}

// Redirect to the selected page
function changePage(page) {
    if (page < 1 || page > totalPages) return; // Ensure page is within valid range
    window.location.href = `catalog?page=${page}`;
}

// Initialize pagination
fetchTotalPages();
