import { Component, OnInit, Input, AfterViewInit } from '@angular/core';
import * as L from 'leaflet';

@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent implements AfterViewInit{
  @Input() latitude!: number;
  @Input() longitude!: number;

  private map!: L.Map;

  constructor() {}

  ngAfterViewInit(): void {
    this.initMap();
  }

  private initMap(): void {
    var map = L.map('map').setView([this.latitude, this.longitude], 13);

    L.tileLayer('https://tile.openstreetmap.org/{z}/{x}/{y}.png', {
        attribution: '&copy; <a href="https://www.openstreetmap.org/copyright">OpenStreetMap</a> contributors'
    }).addTo(map);

    L.marker([this.latitude, this.longitude]).addTo(map)
    .bindPopup('Tutaj znajduje sie wydarzenie!!!')
    .openPopup();
  }
}