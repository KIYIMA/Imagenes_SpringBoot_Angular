import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { Personamodel } from '../model/personamodel';

@Injectable({
  providedIn: 'root'
})
export class UserServiceService {
  URL = 'http://localhost:8080/';

  constructor(private httpClient: HttpClient) { }

  public listaImages(): Observable<Personamodel[]>{
    return this.httpClient.get<Personamodel[]>(this.URL + 'lista');
   }
  public lista(): Observable<Personamodel[]>{
    return this.httpClient.get<Personamodel[]>(this.URL + 'lista');
   }
   public detail(id: number): Observable<Personamodel>{
    return this.httpClient.get<Personamodel>(this.URL + `detail/${id}`);
   }
   public save(persona: Personamodel): Observable<any>{
    return this.httpClient.post<any>(this.URL + 'create', persona);
   }
   public update(id: number, persona: Personamodel): Observable<any>{
    return this.httpClient.put<any>(this.URL + `update/${id}`, persona);
   }
   public delete(id: number): Observable<any>{
    return this.httpClient.delete<any>(this.URL + `delete/${id}`);
   }
}
