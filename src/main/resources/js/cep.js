// document.getElementById('cep').addEventListener('blur', function(event) {
//     var cep = this.value.replace(/\D/g, ''); // Remove tudo o que não é dígito
//
//     if (cep != "") {
//         var validacep = /^[0-9]{8}$/; // Valida o formato do CEP
//
//         if(validacep.test(cep)) {
//             // Preenche os campos com "..." enquanto consulta a API
//             document.getElementById('endereco').value="...";
//             document.getElementById('bairro').value="...";
//             document.getElementById('cidade').value="...";
//             document.getElementById('estado').value="...";
//
//             // Consulta a API ViaCEP
//             fetch(`https://viacep.com.br/ws/${cep}/json/`)
//                 .then(response => response.json())
//                 .then(data => {
//                     if (!("erro" in data)) {
//                         // Atualiza os campos com os valores retornados pela API
//                         document.getElementById('endereco').value=(data.logradouro);
//                         document.getElementById('bairro').value=(data.bairro);
//                         document.getElementById('cidade').value=(data.localidade);
//                         document.getElementById('estado').value=(data.uf);
//                     } else {
//                         // CEP pesquisado não foi encontrado
//                         limpaFormularioCep();
//                         alert("CEP não encontrado.");
//                     }
//                 }).catch(() => {
//                 // Algum erro com a requisição
//                 limpaFormularioCep();
//                 alert("Erro ao buscar o CEP.");
//             });
//         } else {
//             // CEP é inválido
//             limpaFormularioCep();
//             alert("Formato de CEP inválido.");
//         }
//     } else {
//         // CEP sem valor, limpa formulário.
//         limpaFormularioCep();
//     }
// });
//
// function limpaFormularioCep() {
//     // Limpa valores do formulário de CEP.
//     document.getElementById('endereco').value=("");
//     document.getElementById('bairro').value=("");
//     document.getElementById('cidade').value=("");
//     document.getElementById('estado').value=("");
// }
