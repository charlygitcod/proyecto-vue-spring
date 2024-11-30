<template>
  <div class="admin-section">
    <h2>Registrar Entrega de EPP</h2>
    <div class="content-container">
      <div class="form-wrapper">
        <form @submit.prevent="agregarEntrega">
          <div class="form-group">
            <label for="documento">Documento del Empleado:</label>
            <input type="number" v-model="nuevaEntrega.empleado_documento" required class="form-input" />
          </div>
          <div class="form-group">
            <label for="nombre_ope">Nombre del Empleado:</label>
            <input type="text" v-model="nuevaEntrega.nombre_ope" required class="form-input" />
          </div>
          <div class="form-group">
            <label for="nombre_epp">Nombre del EPP:</label>
            <input type="text" v-model="nuevaEntrega.nombre_epp" required class="form-input" />
          </div>
          <div class="form-group">
            <label for="cantidad">Cantidad:</label>
            <input type="number" v-model="nuevaEntrega.cantidad" required class="form-input" />
          </div>
          <div class="cont-btn">
            <button type="submit" class="form-submit-button">Registrar Entrega</button>
          </div>
        </form>
      </div>

      <h3>Lista de Entregas de EPP</h3>
      <div class="table-container">
        <table>
          <thead>
            <tr>
              <th>Empleado Documento</th>
              <th>Nombre Operación</th>
              <th>Nombre EPP</th>
              <th>Cantidad</th>
              <th>Fecha</th>
            </tr>
          </thead>
          <tbody>
            <tr v-for="entrega in entregas" :key="entrega.id_entrega">
              <td>{{ entrega.empleado_documento }}</td>
              <td>{{ entrega.nombre_ope }}</td>
              <td>{{ entrega.nombre_epp }}</td>
              <td>{{ entrega.cantidad }}</td>
              <td>{{ entrega.fecha }}</td>
            </tr>
          </tbody>
        </table>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import axios from 'axios';
import Swal from 'sweetalert2'; // Importar SweetAlert2

// Función para obtener la fecha actual en formato YYYY-MM-DD
const obtenerFechaActual = () => {
  const fecha = new Date();
  return fecha.toISOString().split('T')[0];
};

const nuevaEntrega = ref({
  empleado_documento: '',
  nombre_ope: '',
  nombre_epp: '',
  cantidad: ''
});

const entregas = ref([]);

// Función para agregar una nueva entrega con alertas de SweetAlert2
const agregarEntrega = async () => {
  try {
    // Agregar la fecha actual automáticamente
    const entregaConFecha = {
      ...nuevaEntrega.value,
      fecha: obtenerFechaActual() // Añadir la fecha actual
    };

    const response = await axios.post('http://localhost:8080/entregasepp/insertar', entregaConFecha);
    entregas.value.push(response.data);

    // Mostrar alerta de éxito
    Swal.fire({
      icon: 'success',
      title: 'Registro agregado con éxito',
      text: `Documento del empleado: ${nuevaEntrega.value.empleado_documento}`
    });

    // Limpiar el formulario
    nuevaEntrega.value = {
      empleado_documento: '',
      nombre_ope: '',
      nombre_epp: '',
      cantidad: ''
    };
  } catch (error) {
    console.error('Error al agregar la entrega:', error);

    // Mostrar alerta de error
    Swal.fire({
      icon: 'error',
      title: 'Error al registrar la entrega',
      text: 'Hubo un problema al intentar registrar la entrega. Por favor, inténtalo de nuevo.'
    });
  }
};

// Función para obtener las entregas de la base de datos
const obtenerEntregas = async () => {
  try {
    const response = await axios.get('http://localhost:8080/entregasepp/consultar');
    entregas.value = response.data;
  } catch (error) {
    console.error('Error al obtener las entregas:', error);

    // Mostrar alerta de error al obtener los datos
    Swal.fire({
      icon: 'error',
      title: 'Error al cargar las entregas',
      text: 'No se pudieron cargar las entregas desde el servidor.'
    });
  }
};

// Obtener las entregas al montar el componente
onMounted(() => {
  obtenerEntregas();
});
</script>

<style scoped>
.admin-section {
  padding: 20px;
}

.content-container {
  display: flex;
  flex-direction: column;
}

.form-wrapper {
  margin-bottom: 20px;
}

.form-group {
  text-align: left;
  margin-bottom: 15px;
}

.form-input {
  width: 100%;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 4px;
  margin-top: 5px;
}

.cont-btn {
  display: flex;
  justify-content: space-between;
}

.form-submit-button {
  background-color: #4CAF50;
  color: white;
  width: 100%;
  border: none;
  padding: 10px;
  cursor: pointer;
  border-radius: 4px;
}

.table-container {
  width: 100%;
  margin-top: 20px;
}

table {
  width: 100%;
  background-color: antiquewhite;
  border-collapse: collapse;
}

th, td {
  border: 1px solid #ddd;
  padding: 8px;
  text-align: left;
}

th {
  background-color: #f2f2f2;
}
</style>
