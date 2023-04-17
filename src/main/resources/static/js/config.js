document.addEventListener('DOMContentLoaded', function() {
  const form = document.querySelector('form');
  form.addEventListener('submit', function(e) {
      e.preventDefault(); // previene el envío normal del formulario
      const formData = new FormData(form); // crea un objeto FormData con los valores del formulario
      const data = Object.fromEntries(formData.entries()); // convierte el FormData a un objeto JavaScript
      console.log(data);
      fetch('/configuracion', {
          method: 'POST',
          headers: {
              'Content-Type': 'application/json'
          },
          body: JSON.stringify(data) // envía los datos del formulario en formato JSON
      }).then(response => {
          if (response.ok) {
              alert('Configuración guardada correctamente.');
          } else {
              alert('Error al guardar la configuración.');
          }
      });
  });

  const tabs = document.querySelectorAll('.nav-tabs .nav-link');
  tabs.forEach(tab => {
      tab.addEventListener('click', function(e) {
          e.preventDefault();
          const target = e.currentTarget.dataset.bsTarget;
          const activeTabContent = document.querySelector('.tab-content .active');
          const targetTabContent = document.querySelector(target);

          activeTabContent.classList.remove('active');
          activeTabContent.classList.remove('show');

          targetTabContent.classList.add('active');
          targetTabContent.classList.add('show');
      });
  });
});
