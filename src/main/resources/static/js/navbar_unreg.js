function loadNavbar() {
    const navbarHTML = `
    <header class="navbar paddings">
		<a href="/catalog" class="button-text navbar__button">КАТАЛОГ</a>
		<a href="#" class="button-text navbar__button">МАГАЗИНЫ</a>
		<a href="#" class="button-text navbar__button">КОРЗИНА</a>
		<a href="#" class="button-text navbar__button">МОИ ЗАКАЗЫ</a>
		<a href="#" class="button-text navbar__button navbar__button_login"><img src="../images/login.svg"
				th:src="@{/images/login.svg}" alt="login__icon">ВХОД</a>
	</header>`;
    document.body.insertAdjacentHTML('afterbegin', navbarHTML);
}   

document.addEventListener("DOMContentLoaded", loadNavbar);