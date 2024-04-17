import React,{useEffect, useState} from 'react'
import axios from 'axios';
import Swal from 'sweetalert2';
import withReactContent from 'sweetalert2-react-content';
import { show_alerta } from '../functions';

const ShowEmployees = () => {
    const url = 'http://localhost:8080/employee/';
    const [employees,setEmployees]= useState([]);
    const [employeeId,setEmployeeId]= useState('');
    const [firtName,setFirtName]= useState('');
    const [lastName,setLastName]= useState('');
    const [role,setRole]= useState('');
    const [salary,setSalary]= useState('');
    const [operation,setOperation]= useState(1);
    const [title,setTitle]= useState('');

    useEffect( ()=>{
        getEmployees();
    },[]);

    const getEmployees = async () => {
        const respuesta = await axios.get('http://localhost:8080/employee/');
        console.log('respuesta', respuesta);
        setEmployees(respuesta.data);
    }
    const openModal = (op,id,f_name,l_name,rol,salario) =>{
        setEmployeeId('');
        setFirtName('');
        setLastName('');
        setRole('');
        setSalary('');
        setOperation(op);
        if(op === 1){
            setTitle('Registrar empleado');
        }
        else if(op === 2){
            setTitle('Editar empleado');
            setEmployeeId(id);
            setFirtName(f_name);
            setLastName(l_name);
            setRole(rol);
            setSalary(salario);
        }
        window.setTimeout(function(){
            document.getElementById('nombre').focus();
        },500);
    }
    const validar = () => {
        var parametros;
        var metodo;
        console.log('employeeId', employeeId);
        console.log('firtName', firtName);
        console.log('lastName', lastName);
        console.log('role', role);
        console.log('salary', salary);
        
        if(employeeId.trim() === ''){
            show_alerta('Escribe el id del empleado')
        }
        else if(firtName.trim() === ''){
            show_alerta('Escribe el nombre del empleado')
        }
        else if(lastName.trim() === ''){
            show_alerta('Escribe el apellido del empleado')
        }
        else if(role.trim() === ''){
            show_alerta('Escribe el rol del empleado')
        }
        else if(salary.trim() === ''){
            show_alerta('Escribe el salario del empleado')
        }
        else{
            if(operation === 1){
                parametros= {employeeId:employeeId,firstName:firtName,lastName:lastName,role:role,salary:salary};
                metodo = 'POST';
            }
            else{
                parametros= {employeeId:employeeId,firstName:firtName,lastName:lastName,role:role,salary:salary};
                metodo = 'PUT';
            }
            enviarSolicitud(metodo,parametros);
        }
    }
    const enviarSolicitud = async(metodo,parametros) => {
        let newUrl = url; 
        if(metodo === 'DELETE'){
            newUrl += parametros.employeeId;
            console.log('newUrl', newUrl);
        }
        await axios({ method:metodo, url:newUrl, data:parametros}).then(function(respuesta){
            var tipo = respuesta.data[0];
            var msj = respuesta.data[1];
            show_alerta(msj,tipo);
            if(tipo === 'success'){
                document.getElementById('btnCerrar').click();
                getEmployees();
            }
        })
        .catch(function(error){
            show_alerta('Error en la solicitus', 'error');
            console.log(error);
        });
    }
    const eliminarEmpleado = (employeeId,firstName) => {
        console.log('id',employeeId);
        const MySwal = withReactContent(Swal);
        MySwal.fire({
            title:'¿Seguro de eliminar al empleado '+firstName+' ?',
            icon: 'question',text:'No se podrá dar marcha atras',
            showCancelButton:true,confirmButtonText:'Si, eliminar',cancelButtonText:'Cancelar'
        }).then((result) =>{
            if(result.isConfirmed){
                setEmployeeId(employeeId);
                enviarSolicitud('DELETE',{employeeId:employeeId});
            }
            else{
                show_alerta('El producto NO fue eliminado','info');
            }
        })
    }

  return (
    <div className='App'>
        <div className='container-fluid'>
            <div className='row mt-3'>
                <div className='col-md-4 offset-md-4'>
                    <div className='d-grid mx-auto'>
                        <button onClick={()=> openModal(1)} className='btn btn-dark' data-bs-toggle='modal' data-bs-target='#modalEmployees'>
                            <i className='fa-solid fa-circle-plus'></i> Añadir
                        </button>
                    </div>
                </div>
            </div>
            <div className='row mt-3'>
                <div className='col-12 col-lg-8 offset-0 offset-lg-2'>
                    <div className='table-responsive'>
                        <table className='table table-bordered'>
                            <thead>
                                <tr><th>EmployeeId</th><th>FirtName</th><th>LastName</th><th>Role</th><th>Salary</th></tr>
                            </thead>
                            <tbody className='table-group-divider'>
                                {employees.map( (employee,i)=>(
                                    <tr key={employee.employeeId}>
                                        <td>{employee.employeeId}</td>
                                        <td>{employee.firstName}</td>
                                        <td>{employee.lastName}</td>
                                        <td>{employee.role}</td>
                                        <td>${new Intl.NumberFormat('es-mx').format(employee.salary)}</td>
                                        <td>
                                            <button onClick={()=> openModal(2,employee.employeeId,employee.firstName,employee.lastName,employee.role,employee.salary)} 
                                                 className='btn btn-warning' data-bs-toggle='modal' data-bs-target='#modalEmployees'>
                                                <i className='fa-solid fa-edit'></i>
                                            </button>
                                            &nbsp;
                                            <button onClick={()=> eliminarEmpleado(employee.employeeId,employee.firstName)} className='btn btn-danger'>
                                                <i className='fa-solid fa-trash'></i>
                                            </button>
                                        </td>
                                    </tr>
                                ) )
                                }
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        </div>
        <div id='modalEmployees' className='modal fade' aria-hidden='true'>
            <div className='modal-dialog'>
                <div className='modal-content'>
                    <div className='modal-header'>
                        <label className='h5'>{title}</label>
                        <button type='button' className='btn-close' data-bs-dismiss='modal' arial-label='close'></button>
                    </div>
                    <div className='modal-body'>
                        <input type='hidden' id='id'></input>
                        <div className='input-group mb-3'>
                            <spam className='input-group-text'><i className='fa-solid fa-gift'></i></spam>
                            <input type='type' id='employeeId' className='form-control' placeholder='EmployeeId' value={employeeId} 
                            onChange={(e)=> setEmployeeId(e.target.value)}></input>
                        </div>
                        <div className='input-group mb-3'>
                            <spam className='input-group-text'><i className='fa-solid fa-comment'></i></spam>
                            <input type='type' id='nombre' className='form-control' placeholder='Nombre' value={firtName} 
                            onChange={(e)=> setFirtName(e.target.value)}></input>
                        </div>
                        <div className='input-group mb-3'>
                            <spam className='input-group-text'><i className='fa-solid fa-comment'></i></spam>
                            <input type='type' id='apellido' className='form-control' placeholder='Apellido' value={lastName} 
                            onChange={(e)=> setLastName(e.target.value)}></input>
                        </div>
                        <div className='input-group mb-3'>
                            <spam className='input-group-text'><i className='fa-solid fa-comment'></i></spam>
                            <input type='type' id='roll' className='form-control' placeholder='Roll' value={role} 
                            onChange={(e)=> setRole(e.target.value)}></input>
                        </div>
                        <div className='input-group mb-3'>
                            <spam className='input-group-text'><i className='fa-solid fa-dollar'></i></spam>
                            <input type='type' id='salario' className='form-control' placeholder='Salario' value={salary} 
                            onChange={(e)=> setSalary(e.target.value)}></input>
                        </div>
                        <div className='d-grid col-6 mx-auto'>
                            <button onClick={() => validar()} className='btn btn-success'>
                                <i className='fa-solid fa-floppy-disk'></i> Guardar
                            </button>
                        </div>
                    </div>
                    <div className='modal-footer'>
                        <button type='button' id='btnCerrar' className='btn btn-secondary' data-bs-dismiss='modal'>Cerrar</button>
                    </div>
                </div>
            </div>
        </div>
    </div>
  )
}

export default ShowEmployees
