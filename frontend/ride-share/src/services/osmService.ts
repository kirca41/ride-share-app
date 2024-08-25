import axiosConfig from "../config/axiosConfig";

interface Feature {
    geometry: {
        coordinates: number[]
    },
    properties: {
        display_name: string;
        name: string;
    }
}

interface LocationSearchResponse {
    features: Feature[]
}

export const searchLocation = async (term: string) => {
    const response = await axiosConfig.get<LocationSearchResponse>(
        `https://nominatim.openstreetmap.org/search?city=${term}&format=geojson&addressdetails=0&layer=address&limit=5`
    );

    return response.data.features.map((feature: Feature) => ({
        label: feature.properties.display_name,
        value: feature.properties.name,
        latitude: feature.geometry.coordinates[1],
        longitude: feature.geometry.coordinates[0]
    }));
}