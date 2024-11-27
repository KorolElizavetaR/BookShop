function loadNavbar() {
    const navbarHTML = `
    <header class="navbar paddings">
		<a href="/catalog" class="button-text navbar__button">КАТАЛОГ</a>
		<a href="#" class="button-text navbar__button">МАГАЗИНЫ</a>
		<a href="#" class="button-text navbar__button">КОРЗИНА</a>
		<a href="#" class="button-text navbar__button">МОИ ЗАКАЗЫ</a>
		<a href="#" class="button-text navbar__button navbar__button_login"><img src="../images/login_icon.png"
				th:src="@{/images/login_icon.png}" alt="login__icon">ЛИЧНЫЙ КАБИНЕТ</a>
	</header>`;
    document.body.insertAdjacentHTML('afterbegin', navbarHTML);
}   

document.addEventListener("DOMContentLoaded", loadNavbar);