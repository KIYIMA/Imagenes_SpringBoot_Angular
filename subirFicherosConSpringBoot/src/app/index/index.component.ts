import { Component, OnInit } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { FormControl } from '@angular/forms';
import { Personamodel } from '../model/personamodel';
import { UserServiceService } from '../service/user-service.service';

@Component({
  selector: 'app-index',
  templateUrl: './index.component.html',
  styleUrls: ['./index.component.css']
})
export class IndexComponent implements OnInit {
  nombre = new FormControl('');
  apellido = new FormControl('');
  email = new FormControl('');
  formData = new FormData();

  imagen: any;

  //Endpoint para solicitar una imagen por el nombre
  urlapi = 'http://localhost:8080/photos/';

  userModel: Personamodel[] = [];
  constructor(private httpClient: HttpClient, private userService: UserServiceService) { }

  ngOnInit(): void {
    this.cargarUsuarios();
  }
  cargarUsuarios() {
    this.userService.lista().subscribe(
      data => {
        this.userModel = data;
      }
    );
  }

  enviarFormulario() {
    //Creamos el objeto usuario, con las propiedades de nuestro formulario
    let usuario = { nombre: this.nombre.value, apellido: this.apellido.value, email: this.email.value }
    //Elimino el usuario anterior, de lo contrario se van acumulando en el form-data
    this.formData.delete('usuario');
    //Añadimos a nuestro objeto formData nuestro objeto convertido a String
    this.formData.append("usuario", JSON.stringify(usuario));
    console.log(this.formData.getAll('fichero'));


    //Realizamos la petición a SpringBoot
    this.httpClient.post<any>('http://localhost:8080/users/save', this.formData).subscribe(data => {
      //En este punto nuestra petición ha funcionado correctamente

      //Fuente: https://www.iteramos.com/pregunta/88431/como-refrescar-un-componente-en-angular
      console.log("Usuario creado correctamente");
      this.nombre.reset();
      this.apellido.reset();
      this.email.reset();

      this.ngOnInit();

    });
    console.log(this.formData.get('fichero'));

  }

  onFileSelected(event: any) {

    this.formData.delete('fichero');
    this.formData.append("fichero", event.target.files[0]);

  }
  addImage(event: any, id: number) {
    this.formData.delete('fichero');
    this.formData.append("fichero", event.target.files[0]);

    this.formData.delete('user_id');
    this.formData.append("user_id", JSON.stringify(id));

    this.httpClient.post<any>('http://localhost:8080/photos/add', this.formData).subscribe(data => {
      //En este punto nuestra petición ha funcionado correctamente
      alert("Imagen agregada correctamente");
      this.ngOnInit();
    });

  }
  editImage(event: any, id: number) {
    this.formData.delete('fichero');
    this.formData.append("fichero", event.target.files[0]);

    this.formData.delete('user_id');
    this.formData.append("user_id", JSON.stringify(id));

    this.httpClient.post<any>('http://localhost:8080/photos/edit', this.formData).subscribe(data => {
      //En este punto nuestra petición ha funcionado correctamente
      alert("Imagen editada correctamente");
      this.ngOnInit();
    });

  }
  deleteImage(usuario_id: number) {
    this.httpClient.get<any>('http://localhost:8080/photos/delete/' + usuario_id).subscribe(data => {
      //En este punto nuestra petición ha funcionado correctamente
      alert("Imagen eliminada correctamente");
      this.ngOnInit();
    });
  }


}
