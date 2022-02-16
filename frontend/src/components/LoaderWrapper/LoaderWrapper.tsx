import React from 'react';

interface IOwnProps {
    loading: boolean;
}

class LoaderWrapper extends React.Component<IOwnProps> {
    render() {
        return this.props.loading
            ?(

                <div style={{position: 'relative', height: '100%'}}>
                    Loading...
                </div>
            ) : (
                <>
                    {this.props.children}
                </>
            );
    }
}

export default LoaderWrapper;
