import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { FormControl } from '@angular/forms';
import { Personamodel } from './model/personamodel';
import { UserServiceService } from './service/user-service.service';

@Component({
  selector: 'app-root',
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {
  nombre = new FormControl('');
  apellido = new FormControl('');
  email = new FormControl('');
  formData = new FormData();
   
  imagen: any;

  //Endpoint para solicitar una imagen por el nombre
  urlapi = 'http://localhost:8080/photos/';

  userModel: Personamodel[] = [];;
  constructor(private httpClient: HttpClient, private userService: UserServiceService){}
 
  ngOnInit(): void {
    this.cargarUsuarios();
  }
  cargarUsuarios(){
    this.userService.lista().subscribe(
      data=>{
        this.userModel = data;
      }
    );
  }

  enviarFormulario(){
    //Creamos el objeto usuario, con las propiedades de nuestro formulario
    let usuario = { nombre: this.nombre.value, apellido: this.apellido.value, email:this.email.value }
     
    //Añadimos a nuestro objeto formData nuestro objeto convertido a String
    this.formData.append("usuario", JSON.stringify(usuario));
    //console.log();
 
    //Realizamos la petición a SpringBoot
 
    this.httpClient.post<any>('http://localhost:8080/users/save', this.formData).subscribe(data => {
        //En este punto nuestra petición ha funcionado correctamente
        alert("Usuario creado correctamente");
    });
 
  }
 
  onFileSelected(event:any){
    const file:File = event.target.files[0];
    //Añadimos a nuestro objeto formData nuestro objeto file
    this.formData.append("fichero", file);
  }
}
