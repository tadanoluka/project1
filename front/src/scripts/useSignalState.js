import { Signal, effect } from '@preact/signals-core';
import { useEffect, useState } from 'react';

export function useSignalState(signal) {
    const [state, setState] = useState(signal.value);

    useEffect(() => {
        return effect(() => setState(signal.value));
    }, [signal]);

    return state;
}