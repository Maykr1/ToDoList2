document.addEventListener("DOMContentLoaded", function() {
    const popupMessage = document.body.dataset.popupMessage;
    const toast = document.getElementById("toast-message");

    if (popupMessage) {
        toast.textContent = popupMessage + " Refreshing the page soon...";
        toast.style.display = "block";

        setTimeout(() => {
            window.location.reload();
        }, 3000);
    }
})