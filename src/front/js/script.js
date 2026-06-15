// QUARTOS BACKEND

const quartosContainer =
document.getElementById("quartosContainer");

if(quartosContainer){

    fetch("http://localhost:8080/quartos")
    .then(response => response.json())
    .then(quartos => {

        quartos.forEach(quarto => {

            quartosContainer.innerHTML += `

            <div class="card">

                <img src="https://images.unsplash.com/photo-1560448204-e02f11c3d0e2">

                <div class="card-content">

                    <h3>Quarto ${quarto.tipo}</h3>

                    <p>
                        Capacidade:
                        ${quarto.capacidadeHospedes} hóspedes
                    </p>

                    <div class="hotel-info">

                        <span>
                            <i class="fa-solid fa-bed"></i>
                            ${quarto.quantidadeCamas} camas
                        </span>

                        <span>
                            <i class="fa-solid fa-snowflake"></i>
                            ${quarto.possuiAR ? "Ar-condicionado" : "Sem AR"}
                        </span>

                    </div>

                    <div class="price">
                        R$ ${quarto.valorBase}
                    </div>

                    <button class="reserve-btn">
                        Reservar
                    </button>

                </div>

            </div>

            `;

        });

    });

}