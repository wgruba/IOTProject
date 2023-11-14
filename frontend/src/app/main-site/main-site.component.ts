import { AfterViewInit, Component, ElementRef, ViewChild } from '@angular/core';
import { SwiperContainer } from 'swiper/element';
import { SwiperOptions } from 'swiper/types';
import { Card } from '../models/card.model';
import { Router } from '@angular/router';

@Component({
  selector: 'app-main-site',
  templateUrl: './main-site.component.html',
  styleUrls: ['./main-site.component.scss']
})
export class MainSiteComponent {
  @ViewChild('swiper') swiper!: ElementRef<SwiperContainer>;
  contents1: Card[] = [
    {
      id: 1,
      title: 'Computer',
      description: 'Description about computer...',
      url: 'https://picsum.photos/id/1/640/480',
    },
    {
      id: 2,
      title: 'Building',
      description: 'Building description...',
      url: 'https://picsum.photos/id/101/640/480',
    }, {
      id: 3,
      title: 'Glass over a computer',
      description: 'Description of a glass over a computer',
      url: 'https://picsum.photos/id/201/640/480',
    }, {
      id: 4,
      title: 'Autumn',
      description: 'Description about autumn leaves',
      url: 'https://picsum.photos/id/301/640/480',
    }, {
      id: 5,
      title: 'Balloon',
      description: 'Coloured balloon',
      url: 'https://picsum.photos/id/401/640/480',
    },
    {
      id: 1,
      title: 'Computer',
      description: 'Description about computer...',
      url: 'https://picsum.photos/id/1/640/480',
    },
    {
      id: 2,
      title: 'Building',
      description: 'Building description...',
      url: 'https://picsum.photos/id/101/640/480',
    }, {
      id: 3,
      title: 'Glass over a computer',
      description: 'Description of a glass over a computer',
      url: 'https://picsum.photos/id/201/640/480',
    }, {
      id: 4,
      title: 'Autumn',
      description: 'Description about autumn leaves',
      url: 'https://picsum.photos/id/301/640/480',
    }, {
      id: 5,
      title: 'Balloon',
      description: 'Coloured balloon',
      url: 'https://picsum.photos/id/401/640/480',
    },
    {
      id: 1,
      title: 'Computer',
      description: 'Description about computer...',
      url: 'https://picsum.photos/id/1/640/480',
    },
    {
      id: 2,
      title: 'Building',
      description: 'Building description...',
      url: 'https://picsum.photos/id/101/640/480',
    }, {
      id: 3,
      title: 'Glass over a computer',
      description: 'Description of a glass over a computer',
      url: 'https://picsum.photos/id/201/640/480',
    }, {
      id: 4,
      title: 'Autumn',
      description: 'Description about autumn leaves',
      url: 'https://picsum.photos/id/301/640/480',
    }, {
      id: 5,
      title: 'Balloon',
      description: 'Coloured balloon',
      url: 'https://picsum.photos/id/401/640/480',
    },
  ];

  contents2: Card[] = [
    {
      id: 1,
      title: 'Computer',
      description: 'Description about computer...',
      url: 'https://picsum.photos/id/1/640/480',
    },
    {
      id: 2,
      title: 'Building',
      description: 'Building description...',
      url: 'https://picsum.photos/id/101/640/480',
    }, {
      id: 3,
      title: 'Glass over a computer',
      description: 'Description of a glass over a computer',
      url: 'https://picsum.photos/id/201/640/480',
    }, {
      id: 4,
      title: 'Autumn',
      description: 'Description about autumn leaves',
      url: 'https://picsum.photos/id/301/640/480',
    }, {
      id: 5,
      title: 'Balloon',
      description: 'Coloured balloon',
      url: 'https://picsum.photos/id/401/640/480',
    },
  ];
  
  contents3: Card[] = [
    {
      id: 1,
      title: 'Computer',
      description: 'Description about computer...',
      url: 'https://picsum.photos/id/1/640/480',
    },
    {
      id: 2,
      title: 'Building',
      description: 'Building description...',
      url: 'https://picsum.photos/id/101/640/480',
    }, {
      id: 3,
      title: 'Glass over a computer',
      description: 'Description of a glass over a computer',
      url: 'https://picsum.photos/id/201/640/480',
    }, {
      id: 4,
      title: 'Autumn',
      description: 'Description about autumn leaves',
      url: 'https://picsum.photos/id/301/640/480',
    }, {
      id: 5,
      title: 'Balloon',
      description: 'Coloured balloon',
      url: 'https://picsum.photos/id/401/640/480',
    },
  ];


  constructor(private router: Router) {}
  

  index = 0;
  hoverIndices: { [key: string]: number } = {
    swiper1: -1,
    swiper2: -1,
    swiper3: -1,
  }

  // Swiper
  swiperConfig: SwiperOptions = {
    spaceBetween: 20,
    slidesPerView: 5, 
    freeMode: true,
    watchSlidesProgress: true,
    navigation: true,
  }

  ngAfterViewInit() {
    this.swiper.nativeElement.swiper.activeIndex = this.index;
  }

  setHover(swiperId: string, index: number): void {
    this.hoverIndices[swiperId] = index;
  }

  resetHover(swiperId: string): void {
    this.hoverIndices[swiperId] = -1;
  }

  slideChange(swiper: any) {
    this.index = swiper.detail[0].activeIndex;
  }

  navigateToDescriptionSite(card: Card): void {
    this.router.navigate(['/description', card.id]);
  }
}