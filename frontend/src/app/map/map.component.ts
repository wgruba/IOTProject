import { Component, OnInit, Input } from '@angular/core';


@Component({
  selector: 'app-map',
  templateUrl: './map.component.html',
  styleUrls: ['./map.component.scss']
})
export class MapComponent  implements OnInit{
  center: google.maps.LatLngLiteral = {lat: 37.4221, lng: -122.0841};
  zoom = 15;
  mapOptions: google.maps.MapOptions = {
    mapTypeId: 'roadmap',
    scrollwheel: true,
    disableDoubleClickZoom: true,
    maxZoom: 18,
    minZoom: 8,
  };

  constructor() { }

  ngOnInit(): void {
    // Optionally, fetch the location dynamically and update the center
  }
}
