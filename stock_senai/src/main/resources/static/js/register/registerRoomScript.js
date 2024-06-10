// Método que gera os Pop-Ups para o formulário
document.getElementById('form-room-id').addEventListener('submit', function (event) {
    event.preventDefault(); // Impede o envio do formulário para fazer a verificação

    var inputs = document.querySelectorAll('#form-room-id input');
    var allFilled = true;

    inputs.forEach(function (input) {
        if (input.value.trim() === '') {
            allFilled = false;
            input.style.borderColor = 'red'; // Destacar input vazio
        } else {
            input.style.borderColor = ''; // Resetar estilo do input
        }
    });

    if (!allFilled) {
        alert('Por favor, preencha todos os campos.');
    } else {
        alert('Sala cadastrada com sucesso!');
        location.reload();
    }
});
