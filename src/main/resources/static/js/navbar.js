function loadNavbar() {
    const navbarHTML = `
     <div class="navbar paddings">
		<a href="/catalog" class="button-text navbar__button">КАТАЛОГ</a>
		<a href="/shops" class="button-text navbar__button">МАГАЗИНЫ</a>
		<a href="/shopping_bin" class="button-text navbar__button">КОРЗИНА</a>
		<a href="/orders" class="button-text navbar__button">МОИ ЗАКАЗЫ</a>
		<a href="/personal" class="button-text navbar__button navbar__button_login"><img src="../images/login_icon.png"
				th:src="@{/images/login_icon.png}" alt="login__icon">ЛИЧНЫЙ КАБИНЕТ</a>
	</div>`;
    document.body.insertAdjacentHTML('afterbegin', navbarHTML);
}   

document.addEventListener("DOMContentLoaded", loadNavbar);