const API_URL = "http://localhost:8080";

function usuarioLogado(){
    const raw = localStorage.getItem("usuarioLogado");
    return raw ? JSON.parse(raw) : null;
}

function salvarUsuario(usuario){
    localStorage.setItem("usuarioLogado", JSON.stringify(usuario));
}

function sair(){
    localStorage.removeItem("usuarioLogado");
    window.location.href = "login.html";
}

function protegerPagina(){
    if(!usuarioLogado()) window.location.href = "login.html";
}

function atualizarNavbar(){
    const area = document.getElementById("userArea");
    if(!area) return;
    const user = usuarioLogado();
    if(user){
        area.innerHTML = `<span class="user-pill">Olá, ${user.nome}</span><a class="btn btn-secondary btn-small" href="dashboard.html">Painel</a><button class="btn btn-small" onclick="sair()">Sair</button>`;
    }else{
        area.innerHTML = `<a class="btn btn-secondary btn-small" href="login.html">Entrar</a><a class="btn btn-small" href="cadastro.html">Cadastrar</a>`;
    }
}

function dinheiro(valor){
    return Number(valor || 0).toLocaleString('pt-BR', {style:'currency', currency:'BRL'});
}

function mostrarMensagem(id, texto, tipo="ok"){
    const el = document.getElementById(id);
    if(!el) return;
    el.className = `alert ${tipo}`;
    el.textContent = texto;
}

async function request(url, options={}){
    const resp = await fetch(API_URL + url, {
        headers:{"Content-Type":"application/json"},
        ...options
    });
    if(!resp.ok){
        const texto = await resp.text();
        let mensagem = texto || "Erro na requisição";
        try{
            const json = JSON.parse(texto);
            mensagem = json.mensagem || json.erro || mensagem;
        }catch(e){}
        throw new Error(mensagem);
    }
    return resp.json();
}

atualizarNavbar();

const formLogin = document.getElementById("formLogin");
if(formLogin){
    formLogin.addEventListener("submit", async e => {
        e.preventDefault();
        try{
            const usuario = await request("/auth/login", {
                method:"POST",
                body: JSON.stringify({
                    email: document.getElementById("email").value,
                    senha: document.getElementById("senha").value
                })
            });
            salvarUsuario(usuario);
            window.location.href = "dashboard.html";
        }catch(err){
            mostrarMensagem("msg", err.message, "error");
        }
    });
}

const formCadastro = document.getElementById("formCadastro");
if(formCadastro){
    formCadastro.addEventListener("submit", async e => {
        e.preventDefault();
        try{
            const usuario = await request("/auth/cadastro", {
                method:"POST",
                body: JSON.stringify({
                    nome: nome.value,
                    email: email.value,
                    telefone: telefone.value,
                    senha: senha.value,
                    tipo: tipo.value
                })
            });
            salvarUsuario(usuario);
            window.location.href = "dashboard.html";
        }catch(err){
            mostrarMensagem("msg", err.message, "error");
        }
    });
}

async function carregarImoveis(){
    const container = document.getElementById("imoveisContainer") || document.getElementById("quartosContainer");
    if(!container) return;
    try{
        const params = new URLSearchParams(window.location.search);
        const cidade = params.get("cidade") || document.getElementById("buscaCidade")?.value || "";
        const tipo = params.get("tipo") || document.getElementById("filtroTipo")?.value || "";
        const query = new URLSearchParams();
        if(cidade) query.append("cidade", cidade);
        if(tipo) query.append("tipo", tipo);
        const imoveis = await request(`/imoveis${query.toString() ? `?${query.toString()}` : ""}`);
        if(imoveis.length === 0){
            container.innerHTML = `<div class="empty">Nenhum imóvel encontrado.</div>`;
            return;
        }
        container.innerHTML = imoveis.map(imovel => cardImovel(imovel)).join("");
    }catch(err){
        container.innerHTML = `<div class="empty">Erro ao carregar imóveis. Confira se o back-end Java está rodando.</div>`;
    }
}

function cardImovel(imovel){
    const img = imovel.imagemUrl || "https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=900&q=80";
    return `
    <div class="card">
        <img src="${img}" alt="${imovel.titulo}">
        <div class="card-content">
            <h3>${imovel.titulo}</h3>
            <p>${imovel.bairro || ""} ${imovel.cidade ? "- " + imovel.cidade : ""}</p>
            <div class="hotel-info">
                <span><i class="fa-solid fa-user-group"></i> ${imovel.hospedes} hóspedes</span>
                <span><i class="fa-solid fa-bed"></i> ${imovel.camas} camas</span>
                <span><i class="fa-solid fa-bath"></i> ${imovel.banheiros} banheiros</span>
                ${imovel.wifi ? '<span><i class="fa-solid fa-wifi"></i> Wi-fi</span>' : ''}
                ${imovel.piscina ? '<span><i class="fa-solid fa-water-ladder"></i> Piscina</span>' : ''}
            </div>
            <div class="price">${dinheiro(imovel.precoNoite)} <small>/ noite</small></div>
            <a class="reserve-btn" href="reserva.html?id=${imovel.id}">Ver e reservar</a>
        </div>
    </div>`;
}

const formBusca = document.getElementById("formBusca");
if(formBusca){
    formBusca.addEventListener("submit", e => {
        e.preventDefault();
        const cidade = document.getElementById("buscaCidade").value;
        const tipo = document.getElementById("filtroTipo")?.value || "";
        const query = new URLSearchParams();
        if(cidade) query.append("cidade", cidade);
        if(tipo) query.append("tipo", tipo);
        window.location.href = `hospedagem.html?${query.toString()}`;
    });
}

if(document.getElementById("imoveisContainer") || document.getElementById("quartosContainer")) carregarImoveis();

async function carregarReserva(){
    const box = document.getElementById("detalheImovel");
    if(!box) return;
    protegerPagina();
    const id = new URLSearchParams(window.location.search).get("id");
    try{
        const imovel = await request(`/imoveis/${id}`);
        box.innerHTML = `
            <div class="card"><img src="${imovel.imagemUrl || 'https://images.unsplash.com/photo-1560448204-e02f11c3d0e2?auto=format&fit=crop&w=900&q=80'}"><div class="card-content">
            <h2>${imovel.titulo}</h2><p>${imovel.descricao || ''}</p><p><strong>${imovel.cidade}</strong> - ${imovel.endereco || ''}</p>
            <div class="hotel-info"><span>${imovel.hospedes} hóspedes</span><span>${imovel.quartos} quartos</span><span>${imovel.camas} camas</span></div>
            <div class="price">${dinheiro(imovel.precoNoite)} <small>/ noite</small></div></div></div>`;
        document.getElementById("imovelId").value = imovel.id;
    }catch(err){ box.innerHTML = `<div class="empty">Imóvel não encontrado.</div>`; }
}
carregarReserva();

const formReserva = document.getElementById("formReserva");
if(formReserva){
    formReserva.addEventListener("submit", async e => {
        e.preventDefault();
        const user = usuarioLogado();
        try{
            const reserva = await request("/reservas", {
                method:"POST",
                body: JSON.stringify({
                    usuario:{id:user.id},
                    imovel:{id:Number(imovelId.value)},
                    checkin: checkin.value,
                    checkout: checkout.value,
                    quantidadeHospedes: Number(quantidadeHospedes.value)
                })
            });
            mostrarMensagem("msg", `Reserva confirmada! Valor total: ${dinheiro(reserva.valorTotal)}`, "ok");
        }catch(err){ mostrarMensagem("msg", err.message, "error"); }
    });
}

const formImovel = document.getElementById("formImovel");
if(formImovel){
    protegerPagina();
    formImovel.addEventListener("submit", async e => {
        e.preventDefault();
        const user = usuarioLogado();
        try{
            await request("/imoveis", {
                method:"POST",
                body: JSON.stringify({
                    titulo: titulo.value,
                    descricao: descricao.value,
                    cidade: cidade.value,
                    bairro: bairro.value,
                    endereco: endereco.value,
                    imagemUrl: imagemUrl.value,
                    tipo: tipoImovel.value,
                    quartos: Number(quartos.value),
                    banheiros: Number(banheiros.value),
                    camas: Number(camas.value),
                    hospedes: Number(hospedes.value),
                    precoNoite: Number(precoNoite.value),
                    wifi: wifi.checked,
                    piscina: piscina.checked,
                    arCondicionado: arCondicionado.checked,
                    estacionamento: estacionamento.checked,
                    petFriendly: petFriendly.checked,
                    anfitriao:{id:user.id}
                })
            });
            mostrarMensagem("msg", "Imóvel cadastrado com sucesso!", "ok");
            formImovel.reset();
        }catch(err){ mostrarMensagem("msg", err.message, "error"); }
    });
}

async function carregarDashboard(){
    const painel = document.getElementById("painelDashboard");
    if(!painel) return;
    protegerPagina();
    const user = usuarioLogado();
    document.getElementById("nomeUsuario").textContent = user.nome;
    try{
        const reservas = await request(`/reservas/usuario/${user.id}`);
        const meusImoveis = await request(`/imoveis/anfitriao/${user.id}`);
        painel.innerHTML = `
            <div class="stat"><h2>${reservas.length}</h2><p>Minhas reservas</p></div>
            <div class="stat"><h2>${meusImoveis.length}</h2><p>Meus imóveis</p></div>
            <div class="stat"><h2>${user.tipo}</h2><p>Tipo de conta</p></div>`;
        renderTabelaReservas(reservas);
        renderMeusImoveis(meusImoveis);
    }catch(err){ painel.innerHTML = `<div class="empty">Erro ao carregar o painel.</div>`; }
}
carregarDashboard();

function renderTabelaReservas(reservas){
    const tbody = document.getElementById("tabelaReservas");
    if(!tbody) return;
    tbody.innerHTML = reservas.length ? reservas.map(r => `
        <tr><td>${r.imovel?.titulo || '-'}</td><td>${r.checkin}</td><td>${r.checkout}</td><td>${r.quantidadeHospedes}</td><td>${dinheiro(r.valorTotal)}</td><td><span class="badge">${r.status}</span></td><td>${r.status !== "CANCELADA" ? `<button class="btn btn-small" onclick="cancelarReserva(${r.id})">Cancelar</button>` : "-"}</td></tr>
    `).join("") : `<tr><td colspan="7">Nenhuma reserva encontrada.</td></tr>`;
}
function renderMeusImoveis(imoveis){
    const tbody = document.getElementById("tabelaImoveis");
    if(!tbody) return;
    tbody.innerHTML = imoveis.length ? imoveis.map(i => `
        <tr><td>${i.titulo}</td><td>${i.cidade}</td><td>${i.hospedes}</td><td>${dinheiro(i.precoNoite)}</td><td>${i.ativo ? 'Ativo' : 'Inativo'}</td></tr>
    `).join("") : `<tr><td colspan="5">Você ainda não cadastrou imóveis.</td></tr>`;
}


async function cancelarReserva(id){
    try{
        await request(`/reservas/${id}/cancelar`, { method:"PUT" });
        alert("Reserva cancelada com sucesso.");
        carregarDashboard();
    }catch(err){
        alert(err.message);
    }
}
