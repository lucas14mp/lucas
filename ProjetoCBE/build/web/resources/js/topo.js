document.addEventListener("DOMContentLoaded", () => {
    document.querySelector(".tela-preta-loading").style.display = "none";
});

function verificarMes() {
    const dataAtual = new Date();
    const mesAtual = dataAtual.getMonth();
    const imgConscientiza = document.getElementById("imgConscientiza");
    const imgSetembro = document.getElementById("imgSetembro");
    const logoBranca = document.getElementById("logoBranca");
    const logoAzul = document.getElementById("logoAzul");
    const header = document.querySelector('header');
    const h2 = document.querySelector('h2');
    const h5 = document.querySelector('h5');
//                const mesAtual = 9;
    console.log("Mês atual: " + mesAtual);
    if (mesAtual === 8) {
        imgSetembro.style.display = "block";
        imgConscientiza.style.display = "none";
        header.style.backgroundColor = '#fce408';
        logoBranca.style.display = 'none';
        logoAzul.style.display = 'block';
        header.style.color = '#2e3192';
        h2.style.color = '#2e3192';
        h5.style.display = 'block';
    }
    if (mesAtual === 9) {
        imgConscientiza.style.display = "block";
        imgSetembro.style.display = "none";
        header.style.backgroundColor = '#fc8b9f';
    }
    if (mesAtual === 10) {
        imgConscientiza.style.display = "block";
        imgSetembro.style.display = "none";
    } else if (mesAtual !== 8 && mesAtual !== 9 && mesAtual !== 10) {
        imgConscientiza.style.display = "none";
        imgSetembro.style.display = "none";
    }
}
verificarMes();
